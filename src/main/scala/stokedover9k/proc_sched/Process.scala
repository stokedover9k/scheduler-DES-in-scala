package stokedover9k.proc_sched

case class Process
	(
  id: Int, 
  time: Int, 
  burst: Int, 
  cpu: Int, 
  io: Int
  ) {
  
	def run(t: Int) = Process(id, time-t, burst-t, cpu, io)
  
  override def toString = {
		"Process [id=" + id + " time=" + time + " burst=" + burst + " cpu=" + cpu + " io=" + io+ "]"
	}
}


