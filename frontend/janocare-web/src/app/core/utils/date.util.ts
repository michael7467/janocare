export function calculateYearsFromDate(practicingFrom: Date): number {
  const startDate: Date = new Date(practicingFrom);
  const currentDate: Date = new Date();
  const diffMilliseconds: number = currentDate.getDate() - startDate.getDate();
  const diffYears: number = diffMilliseconds / (1000 * 60 * 60 * 24 * 365); // milliseconds per year
  return Math.floor(diffYears);
}
