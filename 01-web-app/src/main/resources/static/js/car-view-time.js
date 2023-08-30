
let userName = document.querySelector('meta[name="userName"]').content;

if (userName !== "anonymousUser") {
  const initialTimestamp = performance.now();
  let carId = document.querySelector('meta[name="carId"]').content;

  window.addEventListener('beforeunload', () => {

    // extract only integer part to fit in java.math.BigInteger
    const timeSpent = Math.floor(performance.now() - initialTimestamp);

    // make a request to the service that will store user data to the db
    let requestBody = {
      username : userName,
      carId : carId,
      timeSpentInMillis : timeSpent
    };

    let token = document.querySelector('meta[name="_csrf"]').content;
    let header = document.querySelector('meta[name="_csrf_header"]').content;

    fetch('http://localhost:8080/userTimeSpent/viewingCar', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        [header] : [token]
      },
      body: JSON.stringify(requestBody)
    })
        .then(/*DO NOTHING*/)
        .catch(err => console.error(`Error: ${err}`));
  });
}