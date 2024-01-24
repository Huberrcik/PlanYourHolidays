import React, {useState} from 'react';
import { useHistory } from 'react-router-dom';
import { Button } from './Button';
import axios from 'axios';
import { destinationDataToString } from './Helper';

const MyForm = () => {

  const history = useHistory();
  const [errorMessage2, setErrorMessage2] = useState('');

  const [destinationData, setDestinationData] = useState({
    flightTo: '',
    flightFrom: '',
    departureDate: '',
    returnDate: '',
    seats: '',
    radius: '',
    hotelRating: '',
    numberOfRooms:'',
  });

  const [dateError, setDateError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!destinationData.flightTo || !destinationData.flightFrom  || !destinationData.departureDate || !destinationData.returnDate || !destinationData.seats || !destinationData.radius || !destinationData.hotelRating || !destinationData.numberOfRooms) {
      setErrorMessage2('All fields are required.');
      return;
    }else{
      setErrorMessage2('');
    }

    const isDateValid = validateDate(destinationData.departureDate) && validateDate(destinationData.returnDate) && validateDateOrder(destinationData.departureDate,destinationData.returnDate);

    if (!isDateValid) {
      return;
    }
  
    const queryString = destinationDataToString(destinationData);

    try{
      console.log('dane przed wyslaniem:',destinationData);
      await axios.get('http://localhost:8080/api/v1/destination', destinationData);
      console.log('dane po wyslaniu:',destinationData);
      setDestinationData ({
        flightTo: '',
        flightFrom: '',
        departureDate: '',
        returnDate: '',
        seats: '',
        radius: '',
        hotelRating: '',
        numberOfRooms:''
      });
      setDateError('');
      history.push('/Result',{someData: queryString});
    } catch(error){
      console.error('Bład podczas wysylania', error);
    }
  };

  const validateDate = (date) => {
    if (!date) {
      return true;
    }

    const currentDate = new Date();
    const selectedDate = new Date(date);

    if (selectedDate < currentDate) {
      setDateError('You have entered a past date!');
      return false;
    }

    return true;
  };

  const validateDateOrder = (departureDate, returnDate) => {
    if (!departureDate || !returnDate) {
      return true;
    }

    const departure = new Date(departureDate);
    const returnD = new Date(returnDate);

    if (returnD < departure) {
      setDateError('Return date cannot be earlier than departure date');
      return false;
    }

    return true;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;

    setDestinationData({
      ...destinationData,
      [name]: value,
    });
  };

  const [selectedOption, setSelectedOption] = useState('');
  const [selectedOption2, setSelectedOption2] = useState('');
  const [selectedOption3, setSelectedOption3] = useState('');
  const [selectedOption4, setSelectedOption4] = useState('');

  const handleOptionChange = (e) => {
    setSelectedOption(e.target.values);
  };
  const handleOption2Change = (e) => {
    setSelectedOption2(e.target.values);
  };
  const handleOption3Change = (e) => {
    setSelectedOption3(e.target.values);
  };
  const handleOption4Change = (e) => {
    setSelectedOption4(e.target.values);
  };

  return (
    <>
    <div className='top-container'>
        <h1>Find your destination!</h1>
    </div>
    <div className='search-container'>
      <div className='forms'>
        <form onSubmit={handleSubmit}>
        {errorMessage2 && <p>{errorMessage2}</p>}
          <div>
          <i className="fa-solid fa-plane-departure" />
          <input
            type="text"
            name="flightFrom"
            pattern="[A-Z]{3}"
            title="Please enter international airport code"
            value={destinationData.flightFrom}
            onChange={handleChange}
            placeholder="Leaving from"
          />
          <i className="fa-solid fa-plane-arrival" />
          <input
            type="text"
            name="flightTo"
            pattern="[A-Z]{3}"
            title="Please enter international airport code"
            value={destinationData.flightTo}
            onChange={handleChange}
            placeholder="Going to"
          />
          </div>
          <div>
          {dateError && <p className="error-message">{dateError}</p>}
          <i className="fa-regular fa-calendar-days"></i>
              <input
                type="text"
                name="departureDate"
                value={destinationData.departureDate}
                onChange={handleChange}
                placeholder="Departure date"
                pattern="\d{4}-\d{2}-\d{2}"
                title="Please enter a date in the format YYYY-MM-DD"
              />
              <i className="fa-regular fa-calendar-days"></i>
              <input
                type="text"
                name="returnDate"
                value={destinationData.returnDate}
                onChange={handleChange}
                placeholder="Arrival date"
                pattern="\d{4}-\d{2}-\d{2}"
                title="Please enter a date in the format YYYY-MM-DD"
              />
            </div>
          <div>
            <i class="fa-solid fa-bed"/>
            <select className='select-input' values={selectedOption} onChanges={handleOptionChange}
            name = "numberOfRooms"
            value={destinationData.numberOfRooms}
            onChange = {handleChange}>
              <option value="" disabled hidden>Numbers of rooms</option>
              <option value="1">1 room</option>
              <option value="2">2 rooms</option>
              <option value="3">3 rooms</option>
              <option value="4">4 rooms</option>
            </select>
            <i class="fa-solid fa-ruler-horizontal"/>
            <select className='select-input' values={selectedOption2} onChanges={handleOption2Change}
            name = "radius"
            value={destinationData.radius}
            onChange = {handleChange}>
              <option value="" disabled hidden>Distance from airport</option>
              <option value="1">Within 1 kilometer</option>
              <option value="5">Within 5 kilometers</option>
              <option value="10">Within 10 kilometers</option>
              <option value="15">Within 15 kilometers</option>
            </select>
            </div>
            <div>
              <i class="fa-solid fa-star"/>
              <select className='select-input' values={selectedOption3} onChanges={handleOption3Change}
              name = "hotelRating"
              value={destinationData.hotelRating}
              onChange = {handleChange}>
                <option value="" disabled hidden>Hotel rating</option>
                <option value="1">1 star</option>
                <option value="2">2 stars</option>
                <option value="3">3 stars</option>
                <option value="4">4 stars</option>
                <option value="5">5 stars</option>
              </select>
              <i class="fa-solid fa-chair"/>
              <select className='select-input' values={selectedOption4} onChanges={handleOption4Change}
              name = "seats"
              value={destinationData.seats}
              onChange = {handleChange}>
                <option value="" disabled hidden>Numbers of seat</option>
                <option value="1">1 seat </option>
                <option value="2">2 seats</option>
                <option value="3">3 seats</option>
                <option value="4">4 seats</option>
                <option value="5">5 seats</option>
              </select>
            </div>
            <div className='button-search'>
            <Button type="submit" className='btns' buttonStyle='btn--outline' buttonSize='btn--large'>
                Search
            </Button>
            </div>
        </form>
      </div>
    </div>
    </>
  );
}

export default MyForm;