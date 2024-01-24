import React from 'react';
import '../App.css';
import './MainPage.css';
import MyUserForm from './UserForm';


function UserPage() {

  return (
    <div className='main-container'>
      <video src='/videos/sky1.mp4' autoPlay loop muted/>
        <MyUserForm/>
    </div>
  )
}
export default UserPage;
