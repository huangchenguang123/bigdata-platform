import { useEffect, useState } from 'react';
import { addColorSchemeListener } from '@/components/Setting';

export function useTheme() {
  const [currentColorScheme, setCurrentColorScheme] = useState(
    localStorage.getItem('theme'),
  );
  useEffect(() => {
    addColorSchemeListener(setCurrentColorScheme);
  }, []);
  return currentColorScheme;
}

export function useOnlyOnceTask(fn: Function) {
  const [isFirst, setIsFirst] = useState(true);
  const [lastData, setLastData] = useState<any>();
  if (isFirst) {
    setIsFirst(false);
    const lastData = fn();
    setLastData(lastData);
    return lastData;
  } else {
    return lastData;
  }
}
