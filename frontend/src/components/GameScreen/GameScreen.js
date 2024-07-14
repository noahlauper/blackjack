import React, {useEffect, useState} from 'react';
import axios from "axios";
import Hand from "../Hand/Hand";
import Button from "react-bootstrap/Button";
import {B1} from '@letele/playing-cards';

const GameScreen = () => {
    const config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS"
        }
    };
    //The Diffrent Phases of The Game
    const Phase = {
        PLAYER1: "player1",
        PLAYER2: "player2",
        inBetween12: "BETWEEN12",
        inBetween21: "BETWEEN21",
        finished: "FINISHED",
    }


    const [player1, setPlayer1] = useState([]);
    const [player1Points, setPlayer1Points] = useState([0])
    const [player2, setPlayer2] = useState([]);
    const [player2Points, setPlayer2Points] = useState([0])
    const [whosTurn, setWhosTurn] = useState(Phase.PLAYER1)
    const [player1Stand, setPlayer1Stand] = useState(false);
    const [player2Stand, setPlayer2Stand] = useState(false);
    const [player1Hited, setPlayer1Hited] = useState(false);
    const [player2Hited, setPlayer2Hited] = useState(false);

    //First Callup it Makes a Request to Start the Game
    useEffect(() => {


        axios
            .get("http://localhost:8080/start/false", config)
            .then(function (response) {
                setPlayer1(response.data.player1Hand);
                setPlayer2(response.data.player2Hand);
                setPlayer1Points(response.data.player1HandValue);
                setPlayer2Points(response.data.player2HandValue)
            });
    }, []);

    //Stops game if Both Players Stand
    useEffect(() => {
        if (player1Stand === true && player2Stand === true) {
            setWhosTurn(Phase.finished)
        }
    }, [player1Stand, player2Stand])

    useEffect(()=>{
        console.log("new Points")
        if (player1Points >= 21){

            setWhosTurn(Phase.finished)
        }
        if (player2Points >= 21){
            setWhosTurn(Phase.finished)
        }
    },[player1Points, player2Points])


    //Request when a Player Hits
    function hit(playerNumb) {
        axios
            .get(`http://localhost:8080/hit/${playerNumb}/true`, config)
            .then(function (response) {
                if (playerNumb == 1) {
                    setPlayer1(response.data.cardDTOS);
                    setPlayer1Points(response.data.handValue);
                    setPlayer1Hited(true);
                }
                if (playerNumb == 2) {
                    setPlayer2(response.data.cardDTOS);
                    setPlayer2Points(response.data.handValue);
                    setPlayer2Hited(true);
                }
            });
    }


    return (
        <div>

            <div className='card-container'>
                {whosTurn === Phase.PLAYER1 && <div>

                    <p>player 1</p>
                    <p>Points: {player1Points}</p>
                    <Hand hand={player1}/>
                    {player1Hited || player1Stand ? <Button onClick={() => setWhosTurn(Phase.inBetween12)}>End Turn</Button> :
                        <Button onClick={() => hit(1)}>Hit</Button>
                    }


                    {!player1Hited && !player1Stand && <Button onClick={() => {
                        setWhosTurn(Phase.inBetween12);
                        setPlayer1Stand(true)
                    }}>Stand</Button>}

                    <p>player 2</p>
                    <div className='card-container'>
                        {player2.map(() => {
                                return <B1 style={{height: '10%', width: '10%'}}/>
                            }
                        )}
                    </div>
                </div>

                }
                {whosTurn === Phase.PLAYER2 && <div>
                    <p>player 1</p>
                    <div className='card-container'>
                        {player1.map(() => {
                                return <B1 style={{height: '10%', width: '10%'}}/>

                            }
                        )}
                    </div>
                    <p>player 2</p>
                    <p>Points: {player2Points}</p>
                    <Hand hand={player2}/>
                    {player2Hited ? <Button onClick={() => setWhosTurn(Phase.inBetween21)}>End Turn</Button> :
                        <Button onClick={() => hit(2)}>Hit</Button>
                    }
                    {!player2Hited  && !player2Stand && <Button onClick={() => {
                        setWhosTurn(Phase.inBetween21);
                        setPlayer2Stand(true)
                    }}>Stand</Button>}

                </div>
                }
                {whosTurn === Phase.inBetween12 &&
                <>
                    <Button onClick={() => { setWhosTurn(Phase.PLAYER2); setPlayer1Hited(false) }}>Show Card</Button>
                </>
                }
                {whosTurn === Phase.inBetween21 && <div>
                    <Button onClick={() =>{ setWhosTurn(Phase.PLAYER1); setPlayer2Hited(false) }}>Show Card</Button>
                </div>
                }

                {
                    whosTurn === Phase.finished &&
                    <>
                        {
                            player1Points === 21 ?
                                <p>Player 1 Won</p>
                                :
                                player2Points === 21 ?
                                    <p>Player 2 Won</p>
                                    :
                                    player1Points > 21 ?
                                        <p>Player 2 Won</p>
                                        :
                                        player2Points > 21 ?
                                            <p>Player 1 Won</p>
                                            :
                                            player1Points > player2Points ?
                                                <p>Player 1 Won</p>
                                                :
                                                player2Points > player1Points ?
                                                    <p>Player 2 Won</p>
                                                    :
                                                    <p>Draw</p>
                        }
                        {
                            <div className='card-container'>
                                <p>Player 1 Points: {player1Points}</p>
                                <Hand hand={player1}/>
                                <p>Player 2 Points: {player2Points}</p>
                                <Hand hand={player2}/>
                            </div>
                        }
                    </>
                }
            </div>
        </div>
    );
};

export default GameScreen;