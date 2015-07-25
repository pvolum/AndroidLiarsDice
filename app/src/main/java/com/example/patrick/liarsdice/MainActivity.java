package com.example.patrick.liarsdice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends ActionBarActivity {
    Random r;
    int claimq;
    int claimf;
    int currentPlayer;
    int diceInPlay;
    ArrayList<Player> playerList;
    ArrayList<String> nameList;
    ListView boardView;
    Button [] dice;
    Button claimButton;
    Button doubtButton;
    Button playAgainButton;
    Button exactButton;
    TextView playerText;
    TextView claimText;
    TextView statusText;
    TextView switchText;
    TextView diceText;
    EditText quant;
    Spinner face;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerList = new ArrayList<Player>();
        boardView = (ListView) findViewById(R.id.boardView);
        nameList = new ArrayList<String>();
        dice = new Button[5];
        handler = new Handler();
        r = new Random();


        claimButton = (Button) findViewById(R.id.claimButton);
        doubtButton = (Button) findViewById(R.id.doubtButton);
        playerText = (TextView) findViewById(R.id.playerText);
        claimText = (TextView) findViewById(R.id.claimText);
        statusText = (TextView) findViewById(R.id.statusText);
        switchText = (TextView) findViewById(R.id.switchText);
        diceText = (TextView) findViewById(R.id.diceText);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        exactButton = (Button) findViewById(R.id.exactButton);

        dice[0] = (Button) findViewById(R.id.die1);
        dice[1] = (Button) findViewById(R.id.die2);
        dice[2] = (Button) findViewById(R.id.die3);
        dice[3] = (Button) findViewById(R.id.die4);
        dice[4] = (Button) findViewById(R.id.die5);

        newGame();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nameList);

        boardView.setAdapter(adapter);
        playAgainButton.setEnabled(false);

        //for (int i = 0; i < playerList.size(); i++){
        //}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    } // onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if ( id == R.id.new_game ) {
            // restart game
            newGame();
        } else if (id == R.id.action_settings) {
            String url = "http://www.wikihow.com/Play-Liar%27s-Dice";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            return true;
        } // else if

        return super.onOptionsItemSelected(item);
    } // onOptionsItemSelected

    public void play(int player){
        diceText.setText("Dice In Play: " + totalDice());
        setDice(player);

        //check end of game condition/ only one person left
        if (playerList.size() == 1){
            statusText.setText("Only one player standing! you win!");
            playAgainButton.setEnabled(true);
        } // if
        else {
            statusText.setText("Player " + (player + 1) + "'s Turn");
            playerText.setText("Player: " + (player + 1));
            enableButtons();
            disableDice();
        } // else

    } // play

    public void claimClick(View v){
        getClaim();
    }

    public void exactClick(View v){
        int count = 0;
        for (int i = 0 ; i < playerList.size(); i++){
            for (int x = 0 ; x < playerList.get(i).hand.size(); x++){
                if (playerList.get(i).hand.get(x) == claimf || playerList.get(i).hand.get(x) == 1){
                    count++;
                } // if
            } // for
        } // for

        if (count == claimq && playerList.get(currentPlayer).hand.size() !=5){
            //exact call was correct so add dice to current players hand
            //if currentPlayer doesnt already have 5 dice
            playerList.get(currentPlayer).hand.add(r.nextInt(6) + 1);
            statusText.setText("Player " + (currentPlayer + 1) + ", wins a dice & starts next round!");
        } // if
        else if(count == claimq && playerList.get(currentPlayer).hand.size() ==5){
            statusText.setText("Player " + (currentPlayer + 1) + ", was correct & starts next round!" );
        } // else if
        else if(count != claimq){
            removeDice(currentPlayer);
            statusText.setText("Player " + (currentPlayer + 1) + ", loses a dice & starts next round");
        } // else if
        else{
            Log.d("error", "this should never appear");
        } // else

        diceText.setText("Dice In Play: " + totalDice() + "    " + "Actual: " + count + " " + claimf + "'s" );


        claimq = 0;
        claimf = 0;

        disableButtons();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                play(currentPlayer);
            }
        }, (4000));

    } // exactClick

    public void newGame() {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.roll);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        // reset core elements of the game to default values
        playAgainButton.setEnabled(false);
        currentPlayer = 0;
        claimq = 0;
        claimf = 0;
        claimText.setText("claim: ");
        doubtButton.setEnabled(false);
        nameList.clear();
        playerList.clear();

        alert.setTitle("Select number of players:")
                .setItems(new CharSequence[] {"2", "3", "4"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mp.start();
                                int value = which + 2;
                                for (int i = 0; i < value; i++) {   //valid # of players
                                    playerList.add(new Player(i + 1));
                                    nameList.add(playerList.get(i).name);
                                    diceInPlay += 5;
                                } // for
                                resetDice();
                                diceText.setText("Dice In Play: " + diceInPlay);
                                dialog.cancel();
                                claimText.setText("Claim: ");
                                play(currentPlayer);
                            }
                        })
                .setIcon(R.drawable.notification_template_icon_bg)
                .setCancelable(false)
                .create()
                .show();
    } // newGame


    public void playAgainClick(View v){

        newGame();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nameList);

        boardView.setAdapter(adapter);
    } // playAgainClick

    public void doubtClick(View v){
        int count = 0;
        for (int i = 0 ; i < playerList.size(); i++){
            for (int x = 0 ; x < playerList.get(i).hand.size(); x++){
                if (playerList.get(i).hand.get(x) == claimf || playerList.get(i).hand.get(x) == 1){
                    count++;
                } // if
            } // for
        } // for

        if (count >= claimq){
            //currentPlayer doubt was wrong and must lose a dice
            removeDice(currentPlayer);

        } // if
        else{
        //previous player made an invalid claim and was called on it. previous player loses a dice.
            if (currentPlayer != 0) {
                removeDice(currentPlayer - 1);
                currentPlayer = currentPlayer - 1;
            } // if
            else {
                removeDice(playerList.size() - 1);
                currentPlayer = playerList.size() - 1;
            } // else
        } // else

        diceText.setText("Dice In Play: " + totalDice() + "    " + "Actual: " + count + " " + claimf + "'s");

        claimq = 0;
        claimf = 0;
        statusText.setText("Player " + (currentPlayer + 1) + " lost a dice and starts next round");
        claimText.setText("Claim: ");

        disableButtons();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                play(currentPlayer);
            }
        }, (4000));

    } // doubtClick

    public void removeDice(int player) {
        playerList.get(player).hand.remove(0);
        if (playerList.get(player).hand.size() == 0){
            //player loses, remove him from playerList
            playerList.remove(player);
        } // if
    } // removeDice


    public void enableButtons(){
        claimButton.setEnabled(true);

        if (claimText.getText() == "Claim: "){
            //resetDice();    //if there is no claim then it is first move in round and dice should be reset
            doubtButton.setEnabled(false);
            exactButton.setEnabled(false);
        } // if
        else{
            doubtButton.setEnabled(true);
            exactButton.setEnabled(true);
        } // else
    } // else

    public void disableButtons(){
        playAgainButton.setEnabled(false);
        claimButton.setEnabled(false);
        exactButton.setEnabled(false);
        doubtButton.setEnabled(false);
        disableDice();
    } // disableButtons

    public void hideDice(){
        for (int i = 0 ; i < dice.length; i++){
            dice[i].setText(" ");
        } // for
    } // hideDice

    public void getClaim(){
        LayoutInflater infl = LayoutInflater.from(this);
        final View inflator = infl.inflate(R.layout.claim_layout, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final AlertDialog.Builder error = new AlertDialog.Builder(this);

        alert.setTitle("Make a claim")
            .setCancelable(true)
            .setView(inflator);

        quant = (EditText) inflator.findViewById(R.id.quantity);
        face = (Spinner) inflator.findViewById(R.id.faceValue);
        // Get the layout inflater

        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                int currentQ = Integer.parseInt(quant.getText().toString().trim());
                int currentF = Integer.parseInt(face.getSelectedItem().toString());

                //game rules for valid claim
                if ((currentQ > claimq) || (currentQ == claimq && currentF > claimf)) {
                    claimq = currentQ;
                    claimf = currentF;

                    //transfer control to next player with status text 4 sec interlude
                    if (currentPlayer < (playerList.size() - 1)) {
                        currentPlayer += 1;
                    } else currentPlayer = 0;

                    statusText.setText("Please Pass Phone To Player " + (currentPlayer + 1));
                    disableButtons();
                    hideDice();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            claimText.setText("Claim: " + claimq + " " + claimf + "'s");
                            play(currentPlayer);
                        }
                    }, (4000));

                } else {
                    dialog.cancel();
                    error.setTitle("Invalid Claim")
                        .setMessage("Please try again.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .show();
                }
            }
        })
        .show();
    } // getClaim


    public void enableDice(){
        for (int i = 0 ; i < dice.length ; i++ )
            dice[i].setClickable(true);
    }

    public void disableDice(){
        for (int i = 0 ; i < dice.length ; i++ )
            dice[i].setClickable(false);
    }


    public void setDice(int player){
        for (int i = 0 ; i < playerList.get(player).hand.size() ; i++){
            dice[i].setText("");
            dice[i].setBackground(getDiceDrawable(playerList.get(player).hand.get(i)));
            //dice[i].setText("\n"+Integer.toString(playerList.get(player).hand.get(i)));
        } // for
        for (int i = playerList.get(player).hand.size(); i < dice.length ; i++ ){
            //dice[i].setText("DEAD");
            dice[i].setBackground(getDrawable(R.drawable.dead));
        } // for
    } // setDice

    public Drawable getDiceDrawable(int value){
        if(value == 1){
            return ContextCompat.getDrawable(this, R.drawable.one);
        }else if(value == 2){
            return ContextCompat.getDrawable(this, R.drawable.two);
        }else if (value == 3){
            return ContextCompat.getDrawable(this, R.drawable.three);
        }else if (value == 4){
            return ContextCompat.getDrawable(this, R.drawable.four);
        }else if (value == 5){
            return ContextCompat.getDrawable(this, R.drawable.five);
        }else{
            return ContextCompat.getDrawable(this, R.drawable.six);
        }
    } // getDiceDrawable

    public void resetDice(){
        for (int i = 0 ; i <playerList.size() ; i++){
            playerList.get(i).newHand();
        } // for
    } // resetDice

    public int totalDice() {
        int total = 0;

        for (int i = 0 ; i < playerList.size(); i++){
           total += playerList.get(i).hand.size();
        } // for

        return total;
    } // totalDice

} // MainActivity
