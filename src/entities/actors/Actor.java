package entities.actors;

import entities.Entity;
import graphics.Animation;
import logic.actions.Action;
import processing.core.PApplet;

public abstract class Actor extends Entity implements Comparable<Actor> {

    public static final int ENEMY = -1;
    public static final int NPC = 0;
    public static final int HERO = 1;

    protected int spd;
    protected float hp;
    protected float power;
    protected Animation currentAnimation;
    protected Action[] attacks;
    protected boolean isAlive;

    public Actor(float x, float y, float w, float h, int spd, float hp,
                 float power, Animation currentAnimation, Action[] attacks) {
        super(x, y, w, h);
        this.spd = spd;
        this.hp = hp;
        this.power = power;
        this.currentAnimation = currentAnimation;
        this.attacks = attacks;
        this.isAlive = true;
    }

    public float getSpd() {
        return spd;
    }

    public boolean isAlive() {
        return isAlive;
    }

    abstract public int getAllegiance();

    abstract public Actor[] chooseTargets(Action toExecute, Actor[] actors);

    public void act(Actor[] actors) {
        int indexToExecute = (int)(Math.random() * attacks.length);
        Action toExecute = attacks[indexToExecute];
        toExecute.applyTo(chooseTargets(toExecute, actors), power);
    }

    public void adjustHP(float amount) {
        hp += amount;
        if (hp <= 0) {
            isAlive = false;
        }
    }

    abstract public void pos();

    public void go(PApplet display) {
        this.pos();
        currentAnimation.update();
        super.go(display);
    }

    @Override
    public int compareTo(Actor o) {
        return this.spd - o.spd;
    }

}
