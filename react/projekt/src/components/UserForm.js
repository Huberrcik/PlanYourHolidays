import React, {useEffect, useState} from 'react';
import { Button } from './Button';
import { useHistory } from 'react-router-dom';
import { useSession } from './SessionContext';

function MyUserForm({ label }) {
  
  
      
    const [userName, setUserName] = useState('');
    const [userEmail, setUserEmail] = useState('');
    const {isLoggedIn} = useSession();
    const history = useHistory();  

    if(isLoggedIn === false){
    history.push('/register');
  }
  

        useEffect(() => {
            const fetchUserName = async () => {
                try {
                    const response = await fetch('http://localhost:8080/customer/info/name', { credentials: 'include' });
                    if (response.ok) {
                        const data = await response.text();
                        setUserName(data);
                    }
                } catch (error) {
                    console.error('Błąd podczas pobierania informacji o nazwie użytkownika', error);
                }
            };

            const fetchEmail = async () => {
              try {
                  const response = await fetch('http://localhost:8080/customer/info/email', { credentials: 'include' });
                  if (response.ok) {
                      const data = await response.text();
                      setUserEmail(data);
                  }
              } catch (error) {
                  console.error('Błąd podczas pobierania informacji o nazwie użytkownika', error);
              }
          };
    
            fetchUserName();
            fetchEmail();
        }, []);

        

  return (
    <div className='User'>
    <div className='user-container'>
      <div className='forms'>
        <form>
        <div>
            <div className='UserInfo'>
              <h2>User Information</h2>
            </div>
            <div className='UserEmail'>
                <i class="fa-solid fa-envelope"/>
                <label> {userEmail}</label>
            </div>
            <div className='UserEmail'>
              <i className="fa-solid fa-user"/>
                <label> {userName}</label>
            </div>
            </div>
            <div className='button-log-out'>
              <form action="http://localhost:8080/customer/logout" method="get">
                <Button type="submit" className='btns' buttonStyle='btn--outline' buttonSize='btn--large'>
                    Log out!
                </Button>
                </form>
            </div>
        </form>
      </div>
    </div>
    </div>
  );
}

export default MyUserForm;