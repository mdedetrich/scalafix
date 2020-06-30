package scalafix.tests.reflect

import org.scalatest.funsuite.AnyFunSuite
import scalafix.internal.reflect.RuleInstrumentation
import metaconfig.Configured
import scala.meta.inputs.Input

class RuleInstrumentationSuite extends AnyFunSuite {
  def check(name: String, original: String, expected: List[String]): Unit = {
    test(name) {
      val Configured.Ok(obtained) =
        RuleInstrumentation.getRuleFqn(Input.VirtualFile(name, original))
      assert(obtained == expected)
    }
  }

  check(
    "lenient dialect is supported",
    """
      |package a
      |import scalafix.v0._
      |object MyRule extends Rule("MyRule") {
      |  List(
      |    1,
      |    2,
      |  )
      |}
    """.stripMargin,
    List("a.MyRule")
  )
}
