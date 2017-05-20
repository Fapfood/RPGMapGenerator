import Type.Type

class MapField {
  private var list = List[Type]()

  def +(model: Type): List[Type] = {
    list = model :: list
    list
  }

  override def toString: String = list.toString()

  def get(index: Int): Type = list(index)
}
