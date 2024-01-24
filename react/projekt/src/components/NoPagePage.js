import React from 'react';
import '../App.css';
import './MainPage.css';

function NoPagePage() {
  return (
    <div className='main-container'>
      <video src='/videos/sky1.mp4' autoPlay loop muted/>
      <h1>404 not found</h1>
    </div>
  )
}

export default NoPagePage