import React from 'react';
import '../App.css';
import './MainPage.css';
import './FormPage.css';
import MyRegisterForm from './RegisterForm';

function RegisterPage() {

  return (
    <div className='main-container'>
      <video src='/videos/sky1.mp4' autoPlay loop muted/>
      <MyRegisterForm />
      </div>
  )
}

export default RegisterPage;
