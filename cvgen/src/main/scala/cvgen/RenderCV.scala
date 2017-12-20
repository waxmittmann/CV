package cvgen

object RenderCV {
  type Paragraph = String

  trait Renderer[S, T] {
    def render(value: S): T
  }

  trait StatefulRenderer[S, T, U] {
    def render(value: S, state: T): (T, U)
  }

  // Naming an implicit class the same as its parent will cause it to be hidden and not work unless implicitly imported
  implicit class AddRenderMethod(cv: CV) {
    def render[S](implicit renderer: Renderer[CV, S]): S =
      renderer.render(cv)
  }
}
