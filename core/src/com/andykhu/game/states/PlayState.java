package com.andykhu.game.states;

import com.andykhu.game.PipoBird;
import com.andykhu.game.sprite.Bird;
import com.andykhu.game.sprite.Tube;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.awt.Dialog;

public class PlayState extends State {
    public static int SCORE = 0;
    public static boolean zxc = false;
    public static boolean status = true;
    private static final int TUBE_SPACING = 150;
    private static final int TUBE_COUNT = 5;
    private static final int GROUND_Y_OFFSET = -16;
    BitmapFont font;
    private String txt;
    private Bird bird;
    private Texture bg;
    private Texture ground,gameover;
    private Vector2 groundPos1,groundPos2;
    private Array<Tube> tubes;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(0,300);
        cam.setToOrtho(false, PipoBird.WIDTH,PipoBird.HEIGHT);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        gameover = new Texture("gameover.png");
        groundPos1 = new Vector2(-300,GROUND_Y_OFFSET);
        groundPos2 = new Vector2((-300)+ground.getWidth(),GROUND_Y_OFFSET);
        tubes = new Array<Tube>();
        font = new BitmapFont();
        for(int i = 1;i<=TUBE_COUNT;i++){
            tubes.add(new Tube(i*(TUBE_SPACING+ Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            if(status)
                bird.jump();
            else
            {

                status = true;
                gsm.set(new MenuState(gsm));
                Bird.MOVEMENT = 100 ;
                Bird.GRAVITY = -15;
                Bird.velocity.y= 250;
                bird.position.x = 0;
                bird.position.y = 300;
                SCORE = 0;
                bird.status = true;
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x +80;
        if((int)bird.getPosition().x >=201 && (int)bird.getPosition().x <=203)
        {
            if(SCORE==0)
                SCORE++;

        }
        else if((int)bird.getPosition().x >=403 && (int)bird.getPosition().x <=405)
            if(SCORE==1)
                SCORE++;
        for (Tube tube :tubes){

            if(cam.position.x - (cam.viewportWidth/2)>tube.getPosTopTube().x+tube.getTopTube().getWidth())
            {
                SCORE++;
                if(SCORE%10==0 && SCORE!=0)
                    bird.MOVEMENT +=20;
                System.out.println(""+bird.MOVEMENT);
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }
            if(tube.collides(bird.getBounds()) && status) {
                Bird.MOVEMENT = 0 ;
                Bird.velocity.y = 0;
                Bird.GRAVITY = 0;
                status = false;
                bird.changeAnimation();
            }
        }
        if(bird.getPosition().y<=ground.getHeight()+GROUND_Y_OFFSET)
            bird.position.y = ground.getHeight()+GROUND_Y_OFFSET;
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        for(Tube tube: tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x,tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(),tube.getPosBotTube().x,tube.getPosBotTube().y);
        }
        font.setColor(0f,0f,0f,1f);
        font.draw(sb, "" + SCORE, bird.getPosition().x + 70, 450);
        font.getData().setScale(3);
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground,groundPos2.x,groundPos2.y);
        if(status ==false)
        {
            sb.draw(gameover,bird.getPosition().x,250);
        }
        sb.end();

    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        for(Tube tube:tubes)
            tube.dispose();
        System.out.println("Play State Disposed");
    }
    private void updateGround(){
        if(cam.position.x - (cam.viewportWidth/2)> groundPos1.x+ground.getWidth())
            groundPos1.add(ground.getWidth()*2,0);
        if(cam.position.x - (cam.viewportWidth/2)>groundPos2.x+ground.getWidth())
            groundPos2.add(ground.getWidth()*2,0);
    }
}
