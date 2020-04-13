package com.myflappybird.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;

	Texture[] birds;

	Texture toptube,bottomtube;
	int flapState=0;
	float birdY=0;
	float velocity=0;

	int gameState=0;
	float gravity=2;
	float gap =400;

	float maxtubeoffset;


	Random random;
	float tubeVelocity=4;


	int numberoftubes=4;
	float[] tubeX=new float[numberoftubes];
	float[] tubeoffset=new float[numberoftubes];
	float distancebetweentubes;

	Circle birdCircle;
//	ShapeRenderer shapeRenderer;

	Rectangle[] topTubeRect;
	Rectangle[] bottomTubeRect;







	@Override
	public void create () {
		batch = new SpriteBatch();
		background=new Texture("bg.png");
		birds = new Texture[2];

		bottomtube=new Texture("bottomtube.png");
		toptube=new Texture("toptube.png");

		birds[0]=new Texture("bird.png");
		birds[1]=new Texture("bird2.png");
		birdY=Gdx.graphics.getHeight()/2- birds[0].getHeight() /2;

		maxtubeoffset = Gdx.graphics.getHeight()/2-gap/2-100;
		random=new Random();
		distancebetweentubes =Gdx.graphics.getWidth()*3/4;

		 birdCircle=new Circle();
//		shapeRenderer=new ShapeRenderer();

		topTubeRect=new Rectangle[numberoftubes];
		bottomTubeRect=new Rectangle[numberoftubes];






		for(int i=0;i<numberoftubes;i++)
		{

			tubeoffset[i] =(random.nextFloat()- 0.5f) * (Gdx.graphics.getHeight() -gap - 200);

			tubeX[i]=Gdx.graphics.getWidth()/2 - toptube.getWidth()/2 +Gdx.graphics.getWidth()+ i*distancebetweentubes;

			topTubeRect[i]=new Rectangle();
			bottomTubeRect[i]=new Rectangle();

		}



	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


		if(gameState != 0)
		{


			if(Gdx.input.justTouched())
			{
				velocity =-30;


			}
			for(int i=0;i<numberoftubes;i++) {

				if(tubeX[i] < -toptube.getWidth())
				{
					tubeX[i]  += numberoftubes * distancebetweentubes;
					tubeoffset[i] =(random.nextFloat()- 0.5f) * (Gdx.graphics.getHeight() -gap - 200);

				}
				else
				{
					tubeX[i] = tubeX[i] - tubeVelocity;

				}


				batch.draw(toptube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoffset[i]);
				batch.draw(bottomtube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomtube.getHeight() + tubeoffset[i]);
				topTubeRect[i]=new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoffset[i],toptube.getWidth(),toptube.getHeight());
				bottomTubeRect[i]=new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomtube.getHeight() + tubeoffset[i],bottomtube.getWidth(),bottomtube.getHeight());


			}




			if(birdY > 0 || velocity < 0 )
			{
				velocity=velocity+gravity;
				birdY -= velocity;
			}

			if(birdY >= Gdx.graphics.getHeight())
			{
				birdY=birdY-50;
			}


		}
		else
		{
			if(Gdx.input.justTouched())
			{
				gameState =1;

			}
		}
		if(flapState ==0)
		{
			flapState=1;
		}
		else
		{
			flapState=0;
		}



		batch.draw(birds[flapState],Gdx.graphics.getWidth()/2 - birds[flapState].getWidth() /2 ,birdY);


		batch.end();
		birdCircle.set(Gdx.graphics.getWidth()/2,birdY+birds[flapState].getHeight()/2,birds[flapState].getWidth()/2);

//		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//		shapeRenderer.setColor(Color.RED);
//
//		shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);
		for(int i=0;i<numberoftubes;i++) {
//
//			shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoffset[i],toptube.getWidth(),toptube.getHeight());
//			shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomtube.getHeight() + tubeoffset[i],bottomtube.getWidth(),bottomtube.getHeight());


			if(Intersector.overlaps(birdCircle,topTubeRect[i]) || Intersector.overlaps(birdCircle,bottomTubeRect[i]) )
			{
				Gdx.app.log("Collision","Yess");
			}
		}

//		shapeRenderer.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
