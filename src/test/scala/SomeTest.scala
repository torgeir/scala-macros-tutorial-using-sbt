import org.scalatest.FunSuite

class SomeTest extends FunSuite {

  test ("works") {
    class C
    Macros.addMethod(classOf[C], "foo", (x: Int) => x + 2)
    println(new C().foo(2))
    assert(1 == 1)
  }
}
