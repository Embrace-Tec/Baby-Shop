package com.babyshop.backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * @author : Yasith Perera
 * @File: VariationDAO
 * @mailto : yasithishara@gmail.com
 * @created : 2024-12-21, Saturday
 **/
public class VariationDAO {
    public List<Variation> getAllVariations() throws SQLException {
        List<Variation> variations = new ArrayList<>();
        String query = "SELECT * FROM variations";

        try (Connection connection = DBUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Variation variation = new Variation();
                variation.setVariationId(resultSet.getInt("variation_id"));
                variation.setVariationType(resultSet.getString("attribute_name"));
                variation.setVariationValue(resultSet.getString("attribute_value"));
                variations.add(variation);
            }
        }
        return variations;
    }

    public void addVariation(Variation variation) throws SQLException {
        String query = "INSERT INTO variations (attribute_name, attribute_value) VALUES (?, ?)";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, variation.getVariationType());
            preparedStatement.setString(2, variation.getVariationValue());
            preparedStatement.executeUpdate();
        }
    }

    public void updateVariation(Variation variation) throws SQLException {
        String query = "UPDATE variations SET attribute_name = ?, attribute_value = ? WHERE variation_id = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, variation.getVariationType());
            preparedStatement.setString(2, variation.getVariationValue());
            preparedStatement.executeUpdate();
        }
    }

    public void deleteVariation(int variationId) throws SQLException {
        String query = "DELETE FROM variations WHERE variation_id = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, variationId);
            preparedStatement.executeUpdate();
        }
    }
}
