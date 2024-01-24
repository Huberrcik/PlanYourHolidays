import React, { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { Button } from './Button';
import { useSession } from './SessionContext';
import './Navbar.css';



function Navbar() {

    const [click, setclick] = useState(false);
    const [button, setButton] = useState(true);
    const {isLoggedIn} = useSession();
  

    const handleClick = () => setclick(!click);
    const closeMobileMenu = () => setclick(false);

    const showButton = () => {
        if(window.innerWidth <= 960){
            setButton(false);
        } else{
            setButton(true);
        }
    };


    useEffect(() => {
        showButton();

        


    }, []);

    window.addEventListener('resize', showButton);

    return (
        <>
            <nav className='navbar'>
                <div className='navbar-container'>
                    <Link to='/' className='navbar-logo' onClick={closeMobileMenu}>
                        FlightFinds<i class="fa-solid fa-plane"/>
                    </Link>
                    <div className='menu-icon' onClick={handleClick}>
                        <i className={click ? 'fas fa-times' : 'fas fa-bars'} />
                    </div>
                    <ul className={click ? 'nav-menu active' : 'nav-menu'}>
                        <li className='nav-item'>
                            <Link to='/' className='nav-links' onClick={closeMobileMenu}>
                                Home
                            </Link>
                        </li>
                        <li className='nav-item'>
                            <Link to='/searching' className='nav-links' onClick={closeMobileMenu}>
                                Search
                            </Link>
                        </li>
                        <li className='nav-item'>
                        {isLoggedIn ? (
                                <Link to='/user' className='nav-links-mobile' onClick={closeMobileMenu}>
                                    My account
                                </Link>
                            ) : (
                                <Link to='/register' className='nav-links-mobile' onClick={closeMobileMenu}>
                                    Sign up
                                </Link>
                            )}
                        </li>
                    </ul>
                    {isLoggedIn ? (
                                (button && <Link to='/user'>
                                <Button buttonStyle='btn--outline'>MY ACCOUNT</Button>
                                </Link>)
                            ) : (
                                (button && <Link to='/register'>
                            <Button buttonStyle='btn--outline'>SIGN UP</Button>
                                </Link>)
                            )}
                </div>
            </nav>
        </>
    )
}

export default Navbar