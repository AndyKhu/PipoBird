package com.andykhu.game.sprite;

import com.andykhu.game.PipoBird;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Bird {
    public static  int GRAVITY = -15;
    public static  int MOVEMENT = 150;
    public Vector3 position;
    public static Vector3 velocity;
    public static Rectangle bounds;
    private Texture texture,death;
    private Animation birdAnimation;
    private Sound flap;
    public static boolean status = true;


    public Bird(int x, int y){
        position = new Vector3(x,y,0);
        velocity = new Vector3(0,0,0);
        texture = new Texture("jetfly1.png");
        death = new Texture("death.png");
        birdAnimation = new Animation(new TextureRegion(texture),9,0.5f);
        bounds = new Rectangle(x,y,texture.getWidth()/9,texture.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));

    }

    public void update(float dt){
        birdAnimation.update(dt,status);

        if(position.y>0)
        {
            velocity.add(0,GRAVITY,0);
        }
        velocity.scl(dt);
        position.add(MOVEMENT*dt, velocity.y, 0);
        if(position.y<0)
            position.y = 0;
        velocity.scl(1/dt);
        bounds.setPosition(position.x,position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }
    public void jump(){
        velocity.y = 250;
        flap.play(0.5f);
    }
    public Rectangle getBounds(){
        return bounds;
    }
    public void dispose(){
        texture.dispose();

        flap.dispose();
    }
    public void changeAnimation(){
        birdAnimation = new Animation(new TextureRegion(death),5,1f);
        bounds = new Rectangle(position.x,position.y,death.getWidth()/5,death.getHeight());
        status = false;
    }


}
