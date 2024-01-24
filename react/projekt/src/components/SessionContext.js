import { createContext, useContext, useEffect, useState } from 'react';
import axios from 'axios';

const SessionContext = createContext();

export const SessionProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const checkSession = async () => {
    try {
      const response = await axios.get('http://localhost:8080/customer/session', { withCredentials: true });
      const data = response.data;

      if (data === 1) {
        setIsLoggedIn(true);
      } else if (data === 0) {
        setIsLoggedIn(false);
      }
    } catch (error) {
      console.error('Błąd podczas sprawdzania sesji', error);
      setIsLoggedIn(false);
    }
  };

  useEffect(() => {
    // Wywołaj funkcję checkSession przy zamontowaniu komponentu
    checkSession();

    // Ustaw interwał sprawdzania sesji co 10 sekund
    const intervalId = setInterval(checkSession, 5000);

    // Wyczyszczenie interwału przy odmontowywaniu komponentu
    return () => clearInterval(intervalId);
  }, []);

  return (
    <SessionContext.Provider value={{ isLoggedIn }}>
      {children}
    </SessionContext.Provider>
  );
};

export const useSession = () => {
  const context = useContext(SessionContext);
  if (!context) {
    throw new Error('useSession must be used within a SessionProvider');
  }
  return context;
};