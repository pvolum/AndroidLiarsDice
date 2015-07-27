package com.example.patrick.liarsdice;

import android.app.Fragment;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by rcastilla on 7/26/15.
 */
public class RetainedFragment extends Fragment {
    // data we want to retain
    private int claimq;
    private int claimf;
    private int currentPlayer;
    private int diceInPlay;
    private boolean exactlyClicked;
    private ArrayList<Player> playerList;
    private ArrayList<String> nameList;
    private Player ai;
    private boolean ai_in_play;

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    } // onCreate

    // sets the value of claimq
    public void setClaimq(int _claimq) {
        if (_claimq >= 0)
            this.claimq = _claimq;
    } // setData

    // returns the value or claimq
    public int getClaimq() {
        return this.claimq;
    } // getClaimq

    // sets the value of claimf
    public void setClaimf(int _claimf) {
        if (_claimf >= 0)
            this.claimf = _claimf;
    } // setData

    // returns the value or claimf
    public int getClaimf() {
        return this.claimf;
    } // getclaimf

    public void setCurrentPlayer(int _currentPlayer) {
        if (_currentPlayer >= 0)
            this.currentPlayer = _currentPlayer;
    } // setCurrentPlayer

    public int getCurrentPlayer() {
        return this.currentPlayer;
    } // getCurrentPlayer

    public void setDiceInPlay(int _diceInPlay) {
        if (_diceInPlay >= 0)
            this.diceInPlay = _diceInPlay;
    } // diceInPlay

    public int getDiceInPlay() {
        return this.diceInPlay;
    } // getDiceInPlay

    public void setExactlyClicked(boolean _exact) {
        this.exactlyClicked = _exact;
    } // setExactlyClicked

    public boolean getExactlyClicked() {
        return this.exactlyClicked;
    } // getExactlyClicked

    public void setPlayerList(ArrayList<Player> _playerList) {
        if (_playerList != null)
            this.playerList = _playerList;
    } // setPlayerList

    public ArrayList<Player> getPlayerList() {
        return this.playerList;
    } // getPlayerList

    public void setNameList(ArrayList<String> _nameList) {
        if (_nameList != null)
            this.nameList = _nameList;
    } // setNameList

    public ArrayList<String> getNameList() {
        return this.nameList;
    } // getNameList

    public void setAi(Player _ai) {
        if (_ai != null)
            this.ai = _ai;
    } // setAi

    public Player getAi() {
        return ai;
    } // getAi

    public void setAiInPlay(boolean _aiInPlay) {
        this.ai_in_play = _aiInPlay;
    } // setAi_in_play

    public boolean getAiInPlay() {
        return this.ai_in_play;
    } // getAiInPlay

} // RetainedFragment
