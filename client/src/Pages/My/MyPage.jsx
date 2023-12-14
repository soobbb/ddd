/* eslint-disable prettier/prettier */
import { useState } from 'react';
import ProfileUser from '../../Components/Profile/ProfileUser.jsx';
import ProfileSidebar from '../../Components/Profile/ProfileSidebar.jsx';
import ProfileEdit from '../../Components/Profile/ProfileEdit.jsx';
import ProfileDelete from '../../Components/Profile/ProfileDelete.jsx';
import ProfileYourLogins from '../../Components/Profile/ProfileYourLogins.jsx';
import * as S from './MyPage.styled.js';

const MyPage = () => {
  const [seletedComponent, setSelectedComponent] = useState('edit');

  const handleComponentChange = (componentName) => {
    setSelectedComponent(componentName);
  };

  return (
    <div className="mypage">
      <S.MypageWrapper>
        <ProfileUser />
        <S.layout>
          <ProfileSidebar onComponentChange={handleComponentChange} />
          <S.ChangeDisplayContainer>
            {seletedComponent === 'edit' && <ProfileEdit />}
            {seletedComponent === 'delete' && <ProfileDelete />}
            {seletedComponent === 'yourlogins' && <ProfileYourLogins />}
          </S.ChangeDisplayContainer>
        </S.layout>
      </S.MypageWrapper>
    </div>
  );
};
export default MyPage;
