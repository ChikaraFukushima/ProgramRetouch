package ec;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BuyDataBeans;
import beans.ItemDataBeans;
import dao.BuyDAO;
import dao.BuyDetailDAO;

/**
 * 購入履歴画面
 */
@WebServlet("/UserBuyHistoryDetail")
public class UserBuyHistoryDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータの文字コードを指定(一応)
		request.setCharacterEncoding("UTF-8");
		// セッション開始
		HttpSession session = request.getSession();

		try {
			// ログイン時に取得したユーザーIDをセッションから取得
		 int buyid = Integer.parseInt(request.getParameter("buy_id"));

			 //データセット直接
			BuyDataBeans BD = BuyDAO.getBuyDataBeansByBuyId(buyid);
			request.setAttribute("bd", BD);

			//11/29追加アイテム用
			ArrayList<ItemDataBeans> buyIDBList = BuyDetailDAO.getItemDataBeansListByBuyId(buyid);
			request.setAttribute("buyIDBList", buyIDBList);

			//(消す→多分失敗)
			//ArrayList<ItemDataBeans> IDB = BuyDetailDAO.getItemDataBeansListByBuyId(buy_id); //()変更
			//request.setAttribute("idb", IDB);

			request.getRequestDispatcher(EcHelper.USER_BUY_HISTORY_DETAIL_PAGE).forward(request, response);


		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}


	}
}
