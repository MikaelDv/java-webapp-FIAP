package br.com.fiap.web.dao;

import br.com.fiap.web.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDao {

    private Connection conexao;

    public ProdutoDao() {
        this.conexao = ConnectionFactory.obterConexao();
    }
    public Produto inserir(Produto produto){
        PreparedStatement comandoSql = null;
        try{
            String sql = "insert into tbl_produto(codigo, nome, preco, quantidade)" +
                    " values(?,?,?,?)";
            comandoSql = conexao.prepareStatement(sql);
            comandoSql.setInt(1, produto.getCodigo());
            comandoSql.setString(2, produto.getNome());
            comandoSql.setDouble(3, produto.getPreco());
            comandoSql.setInt(4, produto.getQuantidade());

            comandoSql.executeUpdate();
            //conexao.close();
            comandoSql.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return produto;
    }

    public Produto atualizar(Produto produtoNovo){
        PreparedStatement comandoSql = null;
        try {
            String sql = "update tbl_produto set nome = ?, preco = ?, quantidade = ? where codigo = ?";
            comandoSql = conexao.prepareStatement(sql);
            comandoSql.setString(1, produtoNovo.getNome());
            comandoSql.setDouble(2, produtoNovo.getPreco());
            comandoSql.setInt(3, produtoNovo.getQuantidade());
            comandoSql.setInt(4, produtoNovo.getCodigo());
            comandoSql.executeUpdate();
            //conexao.close();
            comandoSql.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return produtoNovo;
    }

    //Buscar por id => SELECT * FROM TBL_PRODUTO WHERE ID_PRODUTO = ?
    public Produto buscarPorId(int id){
        Produto produto = new Produto();
        PreparedStatement comandoSql = null;
        try{
            comandoSql = conexao.prepareStatement("SELECT * FROM tbl_produto WHERE codigo = ?");
            comandoSql.setInt(1, id);
            ResultSet rs = comandoSql.executeQuery();
            if(rs.next()){
                produto.setCodigo(rs.getInt(1));
                produto.setNome(rs.getString(2));
                produto.setPreco(rs.getDouble(3));
                produto.setQuantidade(rs.getInt(4));

            }
            conexao.close();
            comandoSql.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return produto;
    }

    //Listar => SELECT * FROM TBL_ENDERECO
    public List<Produto> listar(){
        List<Produto> produtos = new ArrayList<>();
        PreparedStatement comandoSql = null;
        try{
            comandoSql = conexao.prepareStatement("SELECT * FROM tbl_produto");
            ResultSet rs = comandoSql.executeQuery();
            while (rs.next()){
                Produto produto = new Produto();
                produto.setCodigo(rs.getInt(1));
                produto.setNome(rs.getString(2));
                produto.setPreco(rs.getDouble(3));
                produto.setQuantidade(rs.getInt(4));
                produtos.add(produto);
            }
            conexao.close();
            comandoSql.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return produtos;
    }
}
