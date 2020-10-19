package bbgame.game

import bbgame.logic._
import bbgame.game.graphics._
import processing.core.PApplet
import processing.event.KeyEvent
import java.awt.event.KeyEvent._

class BrickBreaker extends GameEngine {
  private val PaddleWidth = 100
  private val PaddleHeight = 15
  private val BrickWidth = 50
  private val BrickHeight = 20
  private val BrickRows = 7
  private val BrickCols = 7
  private val rand = scala.util.Random

  private var isShowingIntro = true
  private var currentKeyPressed: KeyEvent = _
  private var currentBall: Ball = Ball(defaultWidth / 2, 8 * defaultWidth / 10, 0, 5, defaultWidth, defaultHeight)
  private var currentPaddle = Paddle((defaultWidth - PaddleWidth) / 2, 9 * defaultHeight / 10, PaddleWidth, PaddleHeight, defaultWidth, defaultHeight)
  private var currentBricks: List[Brick] = List()

  def isGameOver: Boolean = currentBricks.isEmpty

  // Init function
  override def setup(): Unit = {
    size(defaultWidth, defaultHeight)
    background(0)
    smooth()
    frameRate(60)
    initBricks()
  }

  // Step function
  override def draw(): Unit = {

    if (isShowingIntro) showIntro()
    else if (!isGameOver) {
      resetBackground()

      updatePaddle()
      updateBall()

      drawBricks()
      drawPaddle()
      drawBall()
    } else showGameOverScreen()
  }

  def gameReset(): Unit = {
    initBricks()
    currentPaddle = Paddle((defaultWidth - PaddleWidth) / 2, 9 * defaultHeight / 10, PaddleWidth, PaddleHeight, defaultWidth, defaultHeight)
    currentBall = currentBall.reset(currentPaddle)
  }

  override def keyPressed(key: KeyEvent): Unit = {
    if (isShowingIntro && keyPressed) isShowingIntro = false
    if (isGameOver && key.getKeyCode == VK_R) gameReset()
    else currentKeyPressed = key
  }

  private def resetBackground(): Unit = {
    background(0)
    smooth()
  }

  private def initBricks(): Unit = {
    for (i <- 0 until BrickRows) {
      for (j <- 0 until BrickCols) {
        currentBricks = currentBricks :+ Brick((i + 1) * defaultWidth / (BrickRows + 2),
          (j + 1) * BrickWidth, BrickWidth, BrickHeight, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), isAlive = true)
      }
    }
  }

  private def updatePaddle(): Unit = {
    if (keyPressed)
      currentPaddle = currentPaddle.update(currentKeyPressed.getKeyCode)
  }

  private def updateBall(): Unit = {
    BallAndPaddleCollision()
    BallAndWallsCollision()
    BallAndBricksCollision()
    currentBall = currentBall.update()
  }

  private def BallAndPaddleCollision(): Unit = {
    if (currentBall.ballHitPaddleLeft(currentPaddle)) {
      currentBall = currentBall.goLeft()
      currentBall = currentBall.flipY()
    }
    if (currentBall.ballHitPaddleRight(currentPaddle)) {
      currentBall = currentBall.goRight()
      currentBall = currentBall.flipY()
    }
  }

  private def BallAndWallsCollision(): Unit = {
    if (currentBall.ballHitRightWall()) currentBall = currentBall.goLeft()
    if (currentBall.ballHitLeftWall()) currentBall = currentBall.goRight()
    if (currentBall.ballHitTopWall()) currentBall = currentBall.flipY()
    if (currentBall.ballHitBottom()) currentBall = currentBall.reset(currentPaddle)
  }

  private def BallAndBricksCollision(): Unit = {
    var newBricksList: List[Brick] = List()
    for (brick <- currentBricks)
      if (brick.isAlive) {
        if (currentBall.ballHitRightBrick(brick)) currentBall = currentBall.goLeft()
        else if (currentBall.ballHitLeftBrick(brick)) currentBall = currentBall.goRight()
        else if (currentBall.ballHitTopBrick(brick) || currentBall.ballHitBottomBrick(brick)) currentBall = currentBall.flipY()
        else newBricksList = newBricksList :+ brick
      }
    currentBricks = newBricksList
  }

  private def drawBricks(): Unit = {
    for (brick <- currentBricks)
      if (brick.isAlive) {
        noStroke()
        fill(brick.Red, brick.Green, brick.Blue)
        rect(brick.x, brick.y, brick.BrickWidth, brick.BrickHeight)
      }
  }

  private def drawBall(): Unit = {
    noStroke()
    fill(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), 255)
    ellipse(currentBall.x, currentBall.y, currentBall.radius * 2, currentBall.radius * 2)
  }

  private def drawPaddle(): Unit = {
    fill(Color.Yellow.red, Color.Yellow.green, Color.Yellow.blue, 255)
    rect(currentPaddle.x, currentPaddle.y, currentPaddle.PaddleWidth, currentPaddle.PaddleHeight)
  }

  private def showGameOverScreen(): Unit = {
    background(0)
    textSize(20)
    text("All bricks are destroyed, you win!", 80, 350)
    text("Press R to play again", 150, 400)
  }

  private def showIntro(): Unit = {
    background(0)
    textSize(17)
    text("Use LEFT and RIGHT arrow keys to control the paddle", 30, 350)
    text("Press any key to continue", 130, 400)
  }
}

object BrickBreaker {
  def main(args: Array[String]): Unit = {
    PApplet.main("bbgame.game.BrickBreaker")
  }
}