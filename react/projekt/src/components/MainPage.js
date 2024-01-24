import React from 'react';
import '../App.css';
import { Button } from './Button';
import './MainPage.css';
import { Link } from 'react-router-dom';

function MainPage() {
  return (
    <div className='main-container'>
      <video src='/videos/sky1.mp4' autoPlay loop muted/>
      <h1>Let us help you find the best travel option.</h1>
      <p>Find your destination below.</p>
      <div className='main-btns'>
        <Link to ="searching">
          <Button className='btns' buttonStyle='btn--primary' buttonSize='btn--large'>
            Get started!
          </Button>
        </Link>
      </div>
    </div>
  )
}

export default MainPage
