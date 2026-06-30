import { SORT_ORDER } from '../model';

const sortCycle: SORT_ORDER[] = ['asc', 'desc', null];

export const getNextSortOrder = (currentSortOrder: SORT_ORDER): SORT_ORDER => {
  const nextIndex = (sortCycle.indexOf(currentSortOrder) + 1) % sortCycle.length;
  return sortCycle[nextIndex];
};
