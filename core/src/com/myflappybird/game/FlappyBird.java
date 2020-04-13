package com.myflappybird.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

	BitmapFont font;
	Texture gameover;


	Texture toptube,bottomtube;
	int flapState=0;
	float birdY=0;
	float velocity=0;

	int gameState=0;
	float gravity=2;
	float gap =400;

	float maxtubeoffset;

	int score=0;
	int scoretube=0;

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
		gameover=new Texture("gameover.png");
		bottomtube=new Texture("bottomtube.png");
		toptube=new Texture("toptube.png");

		birds[0]=new Texture("bird.png");
		birds[1]=new Texture("bird2.png");

		maxtubeoffset = Gdx.graphics.getHeight()/2-gap/2-100;
		random=new Random();
		distancebetweentubes =Gdx.graphics.getWidth()*3/4;

		 birdCircle=new Circle();
//		shapeRenderer=new ShapeRenderer();

		topTubeRect=new Rectangle[numberoftubes];
		bottomTubeRect=new Rectangle[numberoftubes];
		font=new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);

		startGame();









	}

	public void startGame()
	{
		birdY=Gdx.graphics.getHeight()/2- birds[0].getHeight() /2;

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


		if(gameState == 1)
		{
			if(tubeX[scoretube] < Gdx.graphics.getWidth()/2)
			{
				score++;
				if(scoretube<numberoftubes-1)
				{
					scoretube++;
				}
				else
				{
					scoretube=0;
				}
			}


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




			if(birdY > 0)
			{
				velocity=velocity+gravity;
				birdY -= velocity;
			}
			else
			{
				gameState=2;
			}

			if(birdY >= Gdx.graphics.getHeight())
			{
				birdY=birdY-50;
			}


		}
		else if(gameState == 0)
		{
			if(Gdx.input.justTouched())
			{
				gameState =1;

			}
		}
		else if(gameState == 2)
		{
			batch.draw(gameover,Gdx.graphics.getWidth()/2-gameover.getWidth()/2,Gdx.graphics.getHeight()/2-gameover.getHeight()/2);
			if(Gdx.input.justTouched())
			{
				gameState =1;
				startGame();
				score=0;
				scoretube=0;
				velocity=0;
				


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


		font.draw(batch,String.valueOf(score),100,200);
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
				gameState=2;

			}
		}

//		shapeRenderer.end();
		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
