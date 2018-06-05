package play.edge

sealed trait Wall

case object WoodWall extends Wall
case object StoneWall extends Wall
