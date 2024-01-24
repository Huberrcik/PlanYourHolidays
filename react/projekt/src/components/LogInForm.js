import React, { useState, useEffect} from 'react';
import { Button } from './Button';
import axios from 'axios';
import { useHistory } from 'react-router-dom';
import { useSession } from './SessionContext';

const MyLogInForm = () => {

  const {isLoggedIn} = useSession();
  const history = useHistory();  

  if(isLoggedIn === true){
    history.push('/user');
  }

  const [loginData, setLoginData] = useState({
    email: '',
    password: '',
  });
  const [dataLogin, setDataLogin] = useState('');


  useEffect(() => {
    if (dataLogin !== '') {
      console.log('response ', dataLogin);

      setLoginData({
        email: '',
        password: '',
      });
    }
  }, [dataLogin, history]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try{
      console.log('dane przed wyslaniem:',loginData);
      const params = new URLSearchParams(loginData);
      const queryString = params.toString();
      console.log(queryString);
      console.log(params);
      const response = await axios.post('http://localhost:8080/customer/login', params);
      console.log('dane po wyslaniu:', response);
      setDataLogin(response.data);
    } catch(error){
      console.error('Bład podczas wysylania', error);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;

    setLoginData({
      ...loginData,
      [name]: value,
    });
  };

  return (
    <>
    <div className='top-container'>
        <h1>Sign into your account! </h1>
    </div>
    <div className='log-in-container'>

      <div className='forms'>
      <form action="http://localhost:8080/customer/login" method="post">
     
        <div className='login'>
          <i class="fa-solid fa-envelope"/>
          <input type="text" name="email" 
          placeholder='Email'/>
        </div>
        <i class="fa-solid fa-lock"/>
        <input type="password" name="password" 
        placeholder='Password'/>
          <Button type="submit" className='btns' buttonStyle='btn--outline' buttonSize='btn--large'>
            Sign in!
          </Button>
</form>
      </div>
    </div>
    </>
  );
}

export default MyLogInForm;