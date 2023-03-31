import React, {useState} from "react";
import "./App.css";
import "ag-grid-enterprise";
import "ag-grid-community/dist/styles/ag-grid.css";
import "ag-grid-community/dist/styles/ag-theme-alpine.css";
import "ag-grid-community/dist/styles/ag-theme-material.min.css";

import Dashboard from "./components/dashboard";
import LoginForm from "./components/LoginForm";

// Main Function
function App() {

    const [loggedIn, setLoggedIn] = useState(false);


    const handleLogin = (loggedIn) => {
        setLoggedIn(loggedIn);
    };

    const handleLogout = () => {
        setLoggedIn(false);
    }



    return (
        <div className="App">
            {loggedIn ?
                <Dashboard handleLogout={handleLogout}/> : <LoginForm handleLogin={handleLogin}/>}
        </div>
    );
}

export default App;
