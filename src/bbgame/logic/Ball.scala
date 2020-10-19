package bbgame.logic

case class Ball(x: Int,
                y: Int,
                VelocityX: Int,
                VelocityY: Int,
                GridWidth: Int,
                GridHeight: Int) {
  val radius = 10

  def update(): Ball =  Ball(x + VelocityX, y + VelocityY, VelocityX, VelocityY, GridWidth, GridHeight)
  def reset(paddle: Paddle): Ball = Ball(paddle.x + paddle.PaddleWidth / 2, paddle.y - 2 * radius, 0, 5, GridWidth, GridHeight)
  def goLeft(): Ball = Ball(x, y, -5, VelocityY, GridWidth, GridHeight)
  def goRight(): Ball = Ball(x, y, 5, VelocityY, GridWidth, GridHeight)
  def flipY(): Ball = Ball(x, y, VelocityX, -VelocityY, GridWidth, GridHeight)

  def ballHitPaddleLeft(paddle: Paddle): Boolean = y + radius == paddle.y && x >= paddle.x && x <= (paddle.x + paddle.PaddleWidth / 2)
  def ballHitPaddleRight(paddle: Paddle): Boolean = y + radius == paddle.y && x > (paddle.x + paddle.PaddleWidth / 2) && x <= paddle.x + paddle.PaddleWidth

  def ballHitRightWall(): Boolean = x + radius >= GridWidth
  def ballHitLeftWall(): Boolean = x - radius <= 0
  def ballHitTopWall(): Boolean = y - radius <= 0
  def ballHitBottom(): Boolean = y + radius >= GridHeight

  def ballHitLeftBrick(brick: Brick): Boolean = x - radius <= brick.x + brick.BrickWidth && x - radius >= brick.x && y >= brick.y && y <= brick.y + brick.BrickHeight
  def ballHitRightBrick(brick: Brick): Boolean = x + radius >= brick.x && y >= brick.y && x + radius <= brick.x + brick.BrickWidth && y <= brick.y + brick.BrickHeight
  def ballHitTopBrick(brick: Brick): Boolean = y - radius <= brick.y + brick.BrickHeight && y - radius >= brick.y && x >= brick.x && x <= brick.x + brick.BrickWidth
  def ballHitBottomBrick(brick: Brick): Boolean = y + radius >= brick.y && y + radius <= brick.y + brick.BrickHeight && x >= brick.x && x <= brick.x + brick.BrickWidth
}
