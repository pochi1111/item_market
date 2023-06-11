package san.kuroinu.item_market;

import com.mysql.cj.xdevapi.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static san.kuroinu.item_market.Item_market.ds;

public class mysql {
    public boolean withdraw_mysql(String uuid,int price) throws SQLException {
        Connection con = ds.getConnection();
        PreparedStatement pstmt = con.prepareStatement("SELECT balance FROM offlinebank WHERE uuid = ?");
        pstmt.setString(1,uuid);
        ResultSet result = pstmt.executeQuery();
        //resultが空ならfalse
        if (!result.next()) return false;
        //priceだけの金額があるかどうか
        if (result.getInt("balance") < price) return false;
        //priceだけ引く
        PreparedStatement pstmt2 = con.prepareStatement("UPDATE offlinebank SET balance = ? WHERE uuid = ?");
        pstmt2.setInt(1,result.getInt("balance")-price);
        pstmt2.setString(2,uuid);
        pstmt2.executeUpdate();
        //データベースを閉じる
        result.close();
        pstmt.close();
        pstmt2.close();
        con.close();
        return true;
    }
    public boolean deposit_mysql(String uuid,int price) throws SQLException {
        //とりあえずあるかどうか
        Connection con = ds.getConnection();
        PreparedStatement pstmt = con.prepareStatement("SELECT balance FROM offlinebank WHERE uuid = ?");
        pstmt.setString(1,uuid);
        ResultSet result = pstmt.executeQuery();
        //resultが空なら作成とともに入れる
        if (!result.next()){
            PreparedStatement pstmt2 = con.prepareStatement("INSERT INTO offlinebank (uuid,balance) VALUES (?,?)");
            pstmt2.setString(1,uuid);
            pstmt2.setInt(2,price);
            pstmt2.executeUpdate();
            //データベースを閉じる
            result.close();
            pstmt.close();
            pstmt2.close();
            con.close();
            return true;
        }else{
            //priceだけ足す
            PreparedStatement pstmt2 = con.prepareStatement("UPDATE offlinebank SET balance = ? WHERE uuid = ?");
            pstmt2.setInt(1,result.getInt("balance")+price);
            pstmt2.setString(2,uuid);
            pstmt2.executeUpdate();
            //データベースを閉じる
            result.close();
            pstmt.close();
            pstmt2.close();
            con.close();
            return true;
        }
    }
}
