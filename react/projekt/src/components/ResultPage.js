import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import '../App.css';
import './MainPage.css';
import './FormPage.css'

const ResultPage = () => {
  const location = useLocation();
  const someData = location.state?.someData || '';
  const [destination, setDestination] = useState(null);
  const [destinationJSON2, setDestinationJSON2] = useState(null);
  const [parsedDestination, setParsedDestination] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const odpowiedz = await axios.get(someData);
        console.log("toto",odpowiedz);
        console.log('Odpowiedz serwera:', odpowiedz.data);
        setDestination(odpowiedz.data);

        const regex = /(\w+)=((?:\d{4}-\d{2}-\d{2})|(?:'[^']*')|(?:[\d.-]+(?:\.[\d]+)?)),?\s*/g;
        const matches = [...odpowiedz.data.matchAll(regex)];
        const destinationObj = {};
        matches.forEach(match => {
          const key = match[1];
          let value = match[2];
          if (value.startsWith("'") && value.endsWith("'")) {
            value = value.slice(1, -1);
          }
          destinationObj[key] = isNaN(value) ? value : parseFloat(value);
        });

        // Zamień obiekt na JSON
        const destinationJSON = JSON.stringify(destinationObj);
        setDestinationJSON2(destinationJSON);

        console.log(destinationJSON);

        try {
          const obiekt = JSON.parse(destinationJSON);
          setParsedDestination(obiekt);
          console.log("Zmienna jest obiektem JSON:", obiekt);
        } catch (error) {
          console.log("Zmienna nie jest poprawnym obiektem JSON:", error);
        }
      } catch (error) {
        console.error('Wystąpił błąd podczas pobierania danych:', error);
      }
    };



    fetchData();
    console.log('useEffect completed');
  }, [someData]);


  return (
    <div className='main-container'>
      <video src='/videos/sky1.mp4' autoPlay loop muted />
      <div className='User'>
        <div className='user-container'>
          <div className='resultDiv'>
            {parsedDestination ? (
              parsedDestination.bestTotalPrice === 0 ?(
                <p>We're sorry, we couldn't find a suitable offer</p>
              ): (
              <div>
                <i class="fa-solid fa-plane"/>
                <p>Leaving from: {parsedDestination.startPoint}</p>
                <p>Going to: {parsedDestination.destinationPoint}</p>
                <p>Departure date: {parsedDestination.dateOfStart}</p>
                <p>Arrival date: {parsedDestination.dateOfFinish}</p>
                <p>Flight code: {parsedDestination.flightCode}</p>
                <p>Flights price: {parsedDestination.flightsPrice} zł</p>
                <p>Hotel name: {parsedDestination.hotelName}</p>
                <p>Hotel price: {parsedDestination.sleepPrice} zł</p>
                <p>Best total price: {parsedDestination.bestTotalPrice} zł</p>
              </div>
              )
                ) : (
                <p>Loading...</p>
                )}
              </div>
        </div>
      </div>
    </div>
  );
}

export default ResultPage;