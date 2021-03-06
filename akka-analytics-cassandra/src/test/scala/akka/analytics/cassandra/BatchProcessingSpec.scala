package akka.analytics.cassandra

import akka.analytics.cassandra.ProcessingSpec._
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

class BatchProcessingSpec extends ProcessingSpec[SparkContext] {
  override val underTest: SparkContext = new SparkContext(sparkConfig)

  override def execute(sc: SparkContext): Array[(JournalKey, Any)] = {
    val rdd: RDD[(JournalKey, Any)] = underTest.eventTable().cache()
    rdd.sortByKey().collect()
  }

  override protected def afterAll(): Unit = {
    underTest.stop()
    super.afterAll()
  }
}
