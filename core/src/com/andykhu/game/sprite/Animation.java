package com.andykhu.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;


public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;
    private boolean stop= false;

    public Animation(TextureRegion region, int frameCount,float cycleTime){
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth()/frameCount;
        for(int i = 0;i<frameCount;i++)
        {
            frames.add(new TextureRegion(region,i*frameWidth,0,frameWidth,region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime/frameCount;
        frame = 0;
    }
    public void update(float dt,boolean status)
    {
        currentFrameTime += dt;
        if(stop)
        {
            currentFrameTime =0;
        }
        if(currentFrameTime>maxFrameTime){
            frame++;
            currentFrameTime=0;
        }
        if(frame>=frameCount)
        {
            frame=0;
            if(status==false)
            {
                stop = true;
                frame=4;
            }
        }
    }
    public TextureRegion getFrame(){
        return frames.get(frame);
    }
}
