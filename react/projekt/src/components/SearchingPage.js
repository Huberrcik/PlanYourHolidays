import React from 'react';
import '../App.css';
import './MainPage.css';
import './FormPage.css';
import MyForm from './SearchForm';

function SearchingPage() {

  return (
    <div className='main-container'>
      <video src='/videos/sky1.mp4' autoPlay loop muted/>
      <MyForm />
      </div>
  )
}

export default SearchingPage;
