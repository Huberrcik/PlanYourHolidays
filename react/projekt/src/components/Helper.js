export const destinationDataToString = (data) => {
    let resultString = 'http://localhost:8080/api/v1/destination/flightsData&';
  
    for (const key in data) {
      resultString += `${data[key]}&`;
    }
  
    resultString = resultString.slice(0, -1);
  
    return resultString;
  };