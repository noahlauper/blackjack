import './App.css';
import Button from 'react-bootstrap/Button'
import {useState} from "react";
import GameScreen from "./components/GameScreen/GameScreen";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';

function App() {

    const [showGame, setShowGame] = useState(true)

    function startGame() {
      setShowGame(false)
    }

    return (
        <div className="App">
            {showGame ?
                <Button onClick={() => startGame()} variant="primary">Start</Button>
            : <GameScreen/>

            }
        </div>
    );
}

export default App;
