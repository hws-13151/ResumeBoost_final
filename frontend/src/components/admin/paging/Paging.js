import React from 'react'
import jwtAxios from '../../../util/jwtUtils'
import { EC2_URL } from '../../../constans'




// member paging 
const Paging = ({startPage, endPage, currentPage, totalPages, setPageData}) => {

  
  let arr = []
  for (let i = startPage; i <= endPage; i++) {
    arr.push(i)
  }

  const pagingFn = async  (num) => {

    const realNum = num - 1;

    if (realNum === currentPage) {
      console.log("no Effect paging.")

      return
    }

    console.log("paging!!->" + realNum)

    const res = await jwtAxios.get(`http://${EC2_URL}:8090/admin/member?page=${realNum}`)

    const data = res.data.member

    const newCurrentPage = data.number;
    const totalPages = data.totalPages;
    const blockNum = 3;
    const memberList = data.content;
    const startPage = ((Math.floor(newCurrentPage/blockNum) * blockNum) + 1 <= totalPages ? (Math.floor(newCurrentPage/blockNum) * blockNum) + 1 : totalPages);
    const endPage = (startPage + blockNum) - 1 < totalPages ? (startPage + blockNum) - 1 : totalPages;

    
    setPageData({
      startPage: startPage,
      endPage: endPage,
      memberList: memberList,
      currentPage: newCurrentPage,
      totalPages: totalPages
    })

  }



  return (
    <>
      <ul>
        <li onClick={() => {pagingFn(1)}}>처음</li>
        {arr.map((el, idx) => {
          return (
            <li onClick={() => {pagingFn(el)}} key={idx}>{el}</li>
          )
        })}
        <li onClick={() => {pagingFn(totalPages)}}>마지막</li>
      </ul>
    </>
  )
}

export default Paging