package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class Group {
    private final String username;
    private final String password;
    private int coin;
    private int experience;
    private ArrayList<Card> allCards = new ArrayList<>();
    private ArrayList<Card> storage = new ArrayList<>();

    private ArrayList<Card> deck = new ArrayList<>();

    public Group(String username, String password) {
        this.username = username;
        this.password = password;
        this.coin = 300;
        this.experience = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getCoin() {
        return coin;
    }

    public int getExperience() {
        return experience;
    }

    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public ArrayList<Card> getStorage() {
        return storage;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }


    public void addToDeck(Card card) {
        deck.add(card);
    }

    public void addToCards(Card card) {
        allCards.add(card);
    }

    public void addToStorage(Card card) {
        storage.add(card);
    }


    public void removeFromDeck(Card card) {
        deck.remove(card);
    }

    public void removeFromCards(Card card) {
        allCards.remove(card);
    }

    public void removeFromStorage(Card card) {
        storage.remove(card);
    }


    public boolean haveCard(String name) {
        for (Card card : storage) {
            if (card.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    public boolean haveCardInDeck(String name) {
        for (Card card : deck) {
            if (card.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    public boolean haveCardFree(String name) {
        for (Card card : allCards) {
            if (card.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean isDeckFull() {
        return deck.size() == 12;
    }

    public boolean pokemonAlreadyInDeck(String name) {
        for (Card card : deck) {
            if (card instanceof PokemonCard) {
                if (card.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Card getCardFromAllCards(String name) {
        for (Card card : allCards) {
            if (card.getName().equals(name)) {
                return card;
            }
        }
        return null;
    }

    public Card getCardFromDeck(String name) {
        for (Card card : deck) {
            if (card.getName().equals(name)) {
                return card;
            }
        }
        return null;
    }

    public Card getCardFromStorage(String name) {
        for (Card card : storage) {
            if (card.getName().equals(name)) {
                return card;
            }
        }
        return null;
    }

    public void sellCard(String name) {
        Card card;
        if ((card = this.getCardFromDeck(name)) != null) {
            this.removeFromStorage(card);
            this.removeFromDeck(card);
            return;
        }
        card = this.getCardFromAllCards(name);
        this.removeFromStorage(card);
        this.removeFromCards(card);
    }

    public void equipCard(String name) {
        Card card = this.getCardFromAllCards(name);
        this.addToDeck(card);
        this.removeFromCards(card);
    }

    public void unEquipCard(String name) {

        Card card = this.getCardFromDeck(name);
        this.removeFromDeck(card);
        this.addToCards(card);
    }

    public int getMyRank() {
        ArrayList<Group> users = Game.getUsers();
        users.sort(Comparator.comparing(Group::getExperience).reversed().thenComparing(Group::getUsername));
        int rank = 1;
        for (Group user : users) {
            if (user.equals(this)) {
                return rank;
            }
            rank++;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group user = (Group) o;
        return Objects.equals(username, user.username);
    }
}
