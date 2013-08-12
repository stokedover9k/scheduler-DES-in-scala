package stokedover9k.proc_sched.des

class Simulation {

  def currentTime: Int = _currentTime

  def afterDelayDo(delay: Int, doThis: => Unit) = {
    val e = Event(_currentTime + delay, () => doThis)
    queue = insert(queue, e)
  }

  def run: Unit = while (!queue.isEmpty) next()

  private type Action = () => Unit

  private case class Event(time: Int, action: Action)

  private def insert(q: List[Event], e: Event): List[Event] = {
    if (q.isEmpty || q.head.time > e.time) e :: q
    else q.head :: insert(q.tail, e)
  }

  private def next(): Unit = queue match {
    case Event(t, a) :: rest => { queue = rest; _currentTime = t; a() }
    case List()              => ;
  }

  var _currentTime: Int = 0
  private var queue: List[Event] = List()

}
