import React from 'react';
import '../App.css';
import './MainPage.css';
import './FormPage.css';
import MyLogInForm from './LogInForm';

function LogInPage() {

  return (
    <div className='main-container'>
      <video src='/videos/sky1.mp4' autoPlay loop muted/>
      <MyLogInForm />
    </div>
  )
}

export default LogInPage;
