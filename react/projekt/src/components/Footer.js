import React from 'react'
import './Footer.css';
import { Link } from 'react-router-dom';

function Footer() {
  return (
    <div className='footer-container'>
        <div className="footer-links">
            <div className="footer-link-wrapper">
                <div className="footer-link-items">
                    <h2>About us</h2>
                    <p>How it works</p>
                    <p>Who we are</p>
                </div>
                <div className="footer-link-items">
                    <h2>Contact us</h2>
                    <p>Instagram</p>
                    <p>Facebook</p>
                    <p>X</p>
                </div>
                <div className="footer-link-items">
                    <h2>Regulations</h2>
                    <p>Privacy policy</p>
                    <p>Terms of use</p>                    
                </div>                
            </div>
        </div>
        <section className="social-media">
            <div className="social-media-wrap">
                <div className="footer-logo">
                    <Link to='/' className='social-logo'>
                        FlightFinds <i class='fa-solid fa-plane'/>
                    </Link>
                </div>
                <small className="website-rights">FlightFinds © 2024</small>
                <div className="social-icons">
                    <i className='fab fa-facebook-f'/>
                    <i className='fab fa-instagram'/>
                    <i className='fab fa-x'/>
                </div>
            </div>
        </section>
    </div>
  )
}

export default Footer
