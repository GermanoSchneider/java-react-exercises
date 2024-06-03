import { render, screen } from '@testing-library/react';
import AccountOverview from './account-overview';

describe('AccountOverview component', () => {

  it('renders without crashing', () => {
      
    const data = {
      supportContact: {
        name: 'Germano',
        email: 'germano@example.com',
      },
      salesOverview: {
        uploads: 10,
        successfulUploads: 80,
        linesAttempted: 100,
        linesSaved: 70,
        lastUploadDate: 1634707200000,
      },
    };

    render(<AccountOverview data={data} />);
  });

  it('validates prop types', () => {

    const invalidData = {}; 
    expect(() => render(<AccountOverview data={invalidData} />)).toThrow();
  });

});