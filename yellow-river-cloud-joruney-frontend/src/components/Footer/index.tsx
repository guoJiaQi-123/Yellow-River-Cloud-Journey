import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import React from 'react';

const Footer: React.FC = () => {
  return (
    <DefaultFooter
      style={{
        background: 'none',
      }}
      links={[
        {
          key: 'TYUT-SmartViewBI Pro',
          title: 'TYUT-SmartViewBI Pro',
          href: 'https://github.com/guoJiaQi-123/TYUT-SmartViewBI-frontend',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/guoJiaQi-123/TYUT-SmartViewBI-frontend',
          blankTarget: true,
        },
        {
          key: 'TYUT-SmartViewBI',
          title: 'TYUT-SmartViewBI',
          href: 'https://github.com/guoJiaQi-123/tyut-SmartViewBI-backend',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
