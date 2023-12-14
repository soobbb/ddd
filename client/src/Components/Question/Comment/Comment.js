/* eslint-disable prettier/prettier */
/* eslint-disable react/prop-types*/
import * as S from './Comment.styled';
function Comment({ data }) {
  const { createdAt } = data;

  const detailDate = (a) => {
    const milliSeconds = new Date() - a;
    const seconds = milliSeconds / 1000;
    if (seconds < 60) return `${Math.floor(seconds)} secs ago`;
    const minutes = seconds / 60;
    if (minutes < 60) return `${Math.floor(minutes)} mins ago`;
    const hours = minutes / 60;
    if (hours < 24) return `${Math.floor(hours)} hours ago`;
    const days = hours / 24;
    if (days < 7) return `${Math.floor(days)} days ago`;
    const weeks = days / 7;
    if (weeks < 5) return `${Math.floor(weeks)} weeks ago`;
    const months = days / 30;
    if (months < 12) return `${Math.floor(months)} months ago`;
    const years = days / 365;
    return `${Math.floor(years)} years ago`;
  };

  const now = new Date(`${createdAt}z` );
  const nowDate = detailDate(now);

  return (
    <S.Container>
      <S.Comment>{data.commentBody} -</S.Comment>
      {data.writer ? <S.Writer>{data.writer.nickname}</S.Writer> : ''}
      <S.CreatedAt>{nowDate}</S.CreatedAt>
    </S.Container>
  );
}
export default Comment;
