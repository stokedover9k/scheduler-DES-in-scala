package stokedover9k.proc_sched.util

class LoopingIterator[A](start: => Iterator[A]) extends Iterator[A] {
  var current = start

  val hasNext = current.hasNext

  def next: A = {
    if (!current.hasNext) current = start
    current.next
  }
}