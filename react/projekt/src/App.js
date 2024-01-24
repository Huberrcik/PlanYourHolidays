import React from 'react';
import './App.css';
import Navbar from './components/Navbar'
import Footer from './components/Footer';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import Home from './components/pages/Home.js';
import Searching from './components/pages/Searching.js';
import Register from './components/pages/Register.js';
import LogIn from './components/pages/LogIn.js';
import User from './components/pages/User.js';
import Result from './components/pages/Result.js';
import NoPage from './components/pages/NoPage.js';


function App() {
  return (
    <>
      <Router>
        <Navbar/>
          <Switch>
            <Route path='/' exact component = {Home}/>
            <Route path='/home' exact component = {Home}/>
            <Route path='/searching' component = {Searching}/>
            <Route path='/register' component = {Register}/>
            <Route path='/log-in' component = {LogIn}/>
            <Route path='/user' component = {User}/>
            <Route path='/result' component = {Result}/>
            <Route path='*' component = {NoPage}/>
          </Switch>
        <Footer/>
      </Router>
    </>
  );
}

export default App;
