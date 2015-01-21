package baladeva.entities;

import gameframework.drawing.Drawable;
import gameframework.drawing.DrawableImage;
import gameframework.drawing.GameCanvas;
import gameframework.drawing.SpriteManager;
import gameframework.drawing.SpriteManagerDefaultImpl;
import gameframework.game.GameData;
import gameframework.game.GameEntity;
import gameframework.motion.GameMovable;
import gameframework.motion.GameMovableDriverDefaultImpl;
import gameframework.motion.MoveStrategyKeyboard;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class BaladevaPlayer extends GameMovable implements GameEntity, Drawable {

	protected SpriteManager spriteManager;
	protected GameCanvas canvas;
	protected int spriteSize;
	protected Point direction;

	public BaladevaPlayer(GameData data, int x, int y) {
		super();
		this.canvas = data.getCanvas();
		this.spriteSize = data.getConfiguration().getSpriteSize();
		this.spriteManager = new SpriteManagerDefaultImpl(new DrawableImage(
				"/images/level1/Eva.png", canvas), this.spriteSize, 3);
		this.direction = new Point(0, 0);
		this.initSpriteManager();

		this.setPosition(new Point(x, y));

		MoveStrategyKeyboard keyboard = new MoveStrategyKeyboard();
		GameMovableDriverDefaultImpl moveDriver = new GameMovableDriverDefaultImpl();

		moveDriver.setStrategy(keyboard);
		moveDriver.setmoveBlockerChecker(data.getMoveBlockerChecker());

		data.getCanvas().addKeyListener(keyboard);

		setDriver(moveDriver);

	}

	public void initSpriteManager() {
		this.spriteManager.setTypes("down", "left", "right", "up");
		this.spriteManager.setType("down");
		this.spriteManager.reset();
	}

	@Override
	public void draw(Graphics g) {
		this.spriteManager.draw(g, position);
	}

	@Override
	public Rectangle getBoundingBox() {
		Rectangle rectangle = new Rectangle(this.spriteSize, this.spriteSize);
		rectangle.setLocation(position.x * this.spriteSize, position.y
				* this.spriteSize);
		return rectangle;
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		Point d = this.moveDriver.getSpeedVector(this).getDirection();
		if ((!direction.equals(d)) && d.equals(new Point(1, 0))) {
			this.spriteManager.setType("right");
			direction = d;
		} else if ((!direction.equals(d)) && d.equals(new Point(-1, 0))) {
			this.spriteManager.setType("left");
			direction = d;
		} else if ((!direction.equals(d)) && d.equals(new Point(0, -1))) {
			this.spriteManager.setType("up");
			direction =d;
		} else if ((!direction.equals(d)) && d.equals(new Point(0, 1))) {
			this.spriteManager.setType("down");
			direction = d;
		} else if (!(d.equals(new Point(0, 0))) ) {
			this.spriteManager.increment();
		}

	}

}