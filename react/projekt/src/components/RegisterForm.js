import React, { useState, useEffect } from 'react';
import { Button } from './Button';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { useHistory } from 'react-router-dom';
import { useSession } from './SessionContext';

const MyRegisterForm = () => {

  const {isLoggedIn} = useSession();
  const history = useHistory();
  const [errorMessage, setErrorMessage] = useState('');
  const [errorMessage2, setErrorMessage2] = useState('');
  const [dataRegister, setDataRegister] = useState('');

  
  if(isLoggedIn === true){
    history.push('/user');
  }

  const [registerData, setRegisterData] = useState({
    name: '',
    email: '',
    password: '',
    matchingPassword: '',
  });

  const errorData = {
    0: 'okok',
    1: 'Your email address is already taken',
    2: 'Wrong password',
  };

  useEffect(() => {
    if (dataRegister !== '') {
      console.log('response ', dataRegister);
      console.log(errorData[dataRegister]);

      if (dataRegister === 0) {
        history.push('/log-in');
      }

      setErrorMessage(errorData[dataRegister]);

      setRegisterData({
        name: '',
        email: '',
        password: '',
        matchingPassword: '',
      });
    }
  }, [dataRegister, history]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!registerData.email || !registerData.name || !registerData.password || !registerData.matchingPassword) {
      setErrorMessage2('All fields are required.');
      return;
    }else{
      setErrorMessage2('');
    }

    try {
      setDataRegister('');
      console.log('dane przed wyslaniem:', registerData);
      const params = new URLSearchParams(registerData);
      const queryString = params.toString();
      console.log(queryString);
      const response = await axios.post('http://localhost:8080/customer/register', queryString);
      setDataRegister(response.data);
      console.log(dataRegister);
      
    } catch (error) {
      console.error('Błąd podczas wysylania', error);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;

    setRegisterData({
      ...registerData,
      [name]: value,
    });
  };

  return (
    <>
    <div className='top-container'>
        <h1>Create your account!</h1>
    </div>
    <div className='sign-up-container'>
      <div className='forms'>
        <form onSubmit={handleSubmit}>
        {errorMessage2 && <p>{errorMessage2}</p>}
          {dataRegister ? (
            dataRegister === 1 || dataRegister === 2 ? (
            <p>{errorMessage}</p>
            ) : (

          <></>
           )
            ) : null}
            <div className='email'>
              <i class="fa-solid fa-envelope"/>
              <input type="text"
              name='email' 
              value={registerData.email}
              onChange={handleChange}
              placeholder="Email"
              />
              <i class="fa-solid fa-user"/>
              <input type="text"
              name='name'
              placeholder="Name"
              value={registerData.name}
              onChange={handleChange}
              />
            </div>
            <div className='password'>
              <i class="fa-solid fa-lock"/>
              <input type="password"
              placeholder="Password"
              name='password'
              value={registerData.password}
              onChange={handleChange}
              />
              <i class="fa-solid fa-lock"/>
              <input type="password"
              name='matchingPassword'
              placeholder="Confirm password"
              value={registerData.matchingPassword}
              onChange={handleChange}
              />
            </div>
            <div className='button-sign-up'>
              <Button type="submit"s className='btns' buttonStyle='btn--outline' buttonSize='btn--large'>
                Register!
              </Button>
            </div>
            <h2>Already have an account?</h2>
            <div className='button-log-in'>
              <Link to="log-in">
                <Button type='submit' className='btns' buttonStyle='btn--outline' buttonSize='btn--large'>
                  Sign in!
                </Button>
              </Link>
            </div>
        </form>
      </div>
    </div>
    </>
  );
}

export default MyRegisterForm;
