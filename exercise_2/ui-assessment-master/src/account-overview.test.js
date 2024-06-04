import { render, screen } from '@testing-library/react';
import AccountOverview from './account-overview';
import Metric from './components/Metric';
import Contact from './components/Contact';
import Container from './components/Container';

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

describe('AccountOverview component', () => {

  it('renders without crashing', () => {

    render(<AccountOverview data={data} />);
  });
});

test('renders AccountOverview component', () => {

  render(<AccountOverview data={data}/>);

  const expectedTexts = [
    'Account Overview', 
    'YOUR FEEFO SUPPORT CONTACT', 
    'Germano', 
    'germano@example.com', 
    '020 3362 4208',
    'Sales',
    '80%', 
    'Upload Success',
    '70%',
    'Lines Saved'
  ];
  
  expectedTexts.forEach(text => {
    const element = screen.getByText(text);
    expect(element).toBeInTheDocument(text);
  });

});


test('renders Metric component', () => {

  render(<Metric title={data.salesOverview.successfulUploads} subtitle="Upload Success" />);

  const expectedTexts = ['80%', 'Upload Success'];

  expectedTexts.forEach(text => {
    const element = screen.getByText(text);
    expect(element).toBeInTheDocument();
  });

});

test('renders Contact component', () => {

  render(<Contact name={data.supportContact.name} email={data.supportContact.email} />);

  const expectedTexts = ['YOUR FEEFO SUPPORT CONTACT', 'Germano', 'germano@example.com', '020 3362 4208'];

  expectedTexts.forEach(text => {
    const element = screen.getByText(text);
    expect(element).toBeInTheDocument();
  });

});

test('renders Container component', () => {

  render(<Container children={<h1>Container Text</h1>} />);

  const element = screen.getByText("Container Text");
  expect(element).toBeInTheDocument();

});

