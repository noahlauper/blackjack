import React from 'react';
import * as deck from '@letele/playing-cards';
import {forEach} from "react-bootstrap/ElementChildren";
import Button from "react-bootstrap/Button";

const Hand = (props) => {
//Maps the Card to display the Right one
  const getCards =  props.hand.map((card)=> {
      var cardType = card.suit.charAt(0)
      const Card = deck[cardType + card.rank];
            return <Card style={{ height: '10%', width: '10%' }} />
        }
    )

    return (
        <div>
            {getCards}
        </div>
    );
};

export default Hand;