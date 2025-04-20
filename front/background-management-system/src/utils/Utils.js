/**
 * 将小于10的数字前面补0
 * @function
 * @param {number} value
 * @returns {string} 返回补0后的字符串
 */
const complement = (value) => {
  return value < 10 ? `0${value}` : value.toString();
};
/**
 * 格式化时间为XXXX年XX月XX日XX时XX分XX秒
 * @function
 * @returns {string} 返回格式化后的时间
 */
export function formateDate(){
  const time = new Date();
  const year = time.getFullYear();
  const month = complement(time.getMonth() + 1);
  const day = complement(time.getDate());
  const hour = complement(time.getHours());
  const minute = complement(time.getMinutes());
  const second = complement(time.getSeconds());
  //const week = '星期' + '日一二三四五六'.charAt(time.getDay());
  return `${year}年${month}月${day}日 ${hour}:${minute}:${second}`;
};
export default {
    formateDate,
};
