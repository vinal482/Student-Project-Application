import React from 'react';
import {
  View,
  Text,
  TextInput,
  Button,
  StyleSheet,
  TouchableOpacity,
  ActivityIndicator,
  ScrollView,
} from 'react-native';
import {NativeStackScreenProps} from '@react-navigation/native-stack';
import {NativeStackNavigationProps} from '@react-navigation/native-stack';
import {useNavigation} from '@react-navigation/native';
import {RootStackParamList} from '../App';
import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import Icon from 'react-native-vector-icons/FontAwesome';
import Ionicons from 'react-native-vector-icons/Ionicons';
import {Pressable} from 'react-native';

type FacultyProfileProps = NativeStackScreenProps<
  RootStackParamList,
  'FacultyProfile'
>;

const FacultyProfile = ({route}: FacultyProfileProps) => {
  const navigation =
    useNavigation<NativeStackNavigationProp<RootStackParamList>>();
  const [email, setEmail] = React.useState('');
  const [emailError, setEmailError] = React.useState(false);
  const [password, setPassword] = React.useState('');
  const [passwordError, setPasswordError] = React.useState(false);
  const [loading, setLoading] = React.useState(false);
  const [firstName, setFirstName] = React.useState('');
  const [firstNameError, setFirstNameError] = React.useState(false);
  const [lastName, setLastName] = React.useState('');
  const [lastNameError, setLastNameError] = React.useState(false);
  const [mobile, setMobile] = React.useState('');
  const [mobileError, setMobileError] = React.useState(false);
  const [program, setProgram] = React.useState('');
  const [institute, setInstitute] = React.useState('');
  const [dob, setDob] = React.useState('');
  const [gander, setGander] = React.useState('');
  const [profileData, setProfileData] = React.useState({});
  const [role, setRole] = React.useState('');

  const retrieveData = async () => {
    setLoading(true);
    const role = JSON.parse(await AsyncStorage.getItem('role'));
    let emailId = '';
    if (role === '0') {
      setRole('0');
      emailId = JSON.parse(await AsyncStorage.getItem('taEmail'));
    } else {
      setRole('1');
      emailId = JSON.parse(await AsyncStorage.getItem('facultyEmail'));
    }
    // const emailId = JSON.parse(await AsyncStorage.getItem('email'));
    try {
      await setEmail(emailId);
      //   await setPassword(JSON.parse(await AsyncStorage.getItem('password')));
      //   setFirstName(JSON.parse(await AsyncStorage.getItem('firstName')));
      //   setLastName(JSON.parse(await AsyncStorage.getItem('lastName')));
      //   setMobile(JSON.parse(await AsyncStorage.getItem('mobile')));
      console.log('Email:', emailId);
      try {
        // const response = await axios.get(
        //   'http://10.200.6.190:8080/getStudentByEmailId',
        //   {
        //     params: {
        //       Email: emailId,
        //     },
        //   },
        // );
        // console.log('Response:', response.data);
        // setProfileData(response.data);
        // setFirstName(response.data.firstName);
        // setLastName(response.data.lastName);
        // setMobile(response.data.mobileNumber);
        // setProgram(response.data.program);
        // setInstitute(response.data.instituteName);
        // setDob(response.data.dateOfBirth);
        // setGander(response.data.gander);
      } catch (e) {
        console.log('Error:', e);
      }
    } catch (e) {
      console.log('Error:', e);
    } finally {
      setLoading(false);
    }
  };

  React.useEffect(() => {
    retrieveData();
  }, []);

  return (
    <>
      {/* <View style={[styles.bottomNavigationBar, styles.elevation]}>
        <Pressable
          onPress={() => {
            navigation.replace('StudentDashboard');
          }}
          style={{
            flexDirection: 'column',
            alignItems: 'center',
            width: '30%',
          }}>
          <Ionicons name="grid-outline" size={28} color="#6d6d6d" />
          <Text style={{color: '#6d6d6d', fontSize: 16}}>Dashboard</Text>
        </Pressable>
        <Pressable
          onPress={() => {
            navigation.replace('ExploreCourses');
          }}
          style={{
            flexDirection: 'column',
            alignItems: 'center',
            width: '30%',
            fontWeight: 'bold',
          }}>
          <Ionicons name="compass-outline" size={28} color="#6d6d6d" />
          <Text style={{color: '#6d6d6d', fontSize: 16}}>Explore</Text>
        </Pressable>
        <Pressable
          style={{flexDirection: 'column', alignItems: 'center', width: '30%'}}>
          <Ionicons name="person-outline" size={28} color="#1d1d1d" />
          <Text style={{color: '#1d1d1d', fontSize: 16, fontWeight: 'bold'}}>
            Profile
          </Text>
        </Pressable>
      </View> */}
      <View style={{minHeight: '100%', backgroundColor: '#eaeaea'}}>
        <ScrollView style={{width: '100%'}}>
          <View style={styles.container}>
            <View style={styles.displayPictureContainer}></View>
            <View style={styles.profileDetailsCard}>
              {/* // logout Button */}
              <TouchableOpacity
                style={styles.submitButton}
                onPress={async () => {
                  await AsyncStorage.clear();
                  if(role !== '0')
                  navigation.pop();
                  navigation.replace('FacultyTALogin');
                }}>
                <Icon
                  name="sign-out"
                  size={23}
                  color="#DB1313"
                  style={{
                    marginRight: 10,
                    transform: [{rotate: '180deg'}],
                  }}
                />
                <Text
                  style={{
                    color: '#DB1313',
                    fontSize: 18,
                    fontWeight: 'bold',
                  }}>
                  LOGOUT
                </Text>
              </TouchableOpacity>
            </View>
          </View>
        </ScrollView>
        {role === '0' ? (
          <View style={[styles.bottomNavigationBar, styles.elevation]}>
            <Pressable
              onPress={() => {
                navigation.replace('FacultyTADashboard');
              }}
              style={{
                flexDirection: 'column',
                alignItems: 'center',
                width: '30%',
                fontWeight: 'bold',
              }}>
              <Ionicons name="grid-outline" size={28} color="#6d6d6d" />
              <Text
                style={{
                  color: '#6d6d6d',
                  fontSize: 16,
                  //   fontWeight: 'bold',
                }}>
                Dashboard
              </Text>
            </Pressable>
            <Pressable
              onPress={() => {
                navigation.replace('ExploreCourses');
              }}
              style={{
                flexDirection: 'column',
                alignItems: 'center',
                width: '30%',
              }}>
              <Ionicons name="compass-outline" size={28} color="#6d6d6d" />
              <Text style={{color: '#6d6d6d', fontSize: 16}}>Explore</Text>
            </Pressable>
            <Pressable
              style={{
                flexDirection: 'column',
                alignItems: 'center',
                width: '30%',
              }}>
              <Ionicons
                name="person-outline"
                size={28}
                color="#1d1d1d"
                style={{fontWeight: 'bold'}}
              />
              <Text
                style={{color: '#1d1d1d', fontSize: 16, fontWeight: 'bold'}}>
                Profile
              </Text>
            </Pressable>
          </View>
        ) : null}
      </View>
    </>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'flex-start',
    alignItems: 'center',
    backgroundColor: '#eaeaea',
    color: '#2d2d2d',
    paddingBottom: 70,
  },
  displayPictureContainer: {
    width: 100,
    height: 100,
    borderRadius: 50,
    backgroundColor: '#666666',
    marginBottom: 40,
    marginTop: 50,
  },
  profileInformation: {
    felx: 1,
    justifyContent: 'center',
    alignItems: 'flex-start',
    width: '95%',
  },
  title: {
    fontSize: 19,
    marginBottom: 0,
    color: '#3d3d3d',
    fontWeight: 'bold',
    margin: 0,
    padding: 0,
  },
  bottomNavigationBar: {
    width: '100%',
    paddingVertical: 15,
    backgroundColor: '#f5f5f5',
    justifyContent: 'space-between',
    alignItems: 'center',
    flexDirection: 'row',
    paddingHorizontal: 20,
    position: 'absolute',
    bottom: 0,
    zIndex: 100,
  },
  bottomNavigationBarActiveColor: {
    color: '#1d1d1d',
  },
  input: {
    width: '80%',
    padding: 8,
    paddingHorizontal: 16,
    marginTop: 20,
    borderWidth: 1,
    color: '#2d2d2d',
    borderColor: '#5d5d5d',
    borderRadius: 8,
  },
  submitButton: {
    // red color
    backgroundColor: '#aaa',
    color: '#DB1313',
    padding: 12,
    paddingHorizontal: 30,
    borderRadius: 50,
    marginBottom: 20,
    marginTop: 20,
    flexDirection: 'row',
  },
  profileDetailsCard: {
    width: '100%',
    // backgroundColor: '#efefef',
    borderRadius: 8,
    padding: 10,
    marginTop: 10,
    paddingTop: 0,
    marginBottom: 15,
  },
  detailKeyItem: {
    fontSize: 13,
    color: '#7d7d7d',
    fontWeight: 'bold',
  },
  detailValueItem: {
    paddingLeft: 10,
    fontSize: 18,
    color: '#2d2d2d',
  },
  separator: {
    width: '100%',
    marginTop: 7,
    marginBottom: 7,
  },
});

export default FacultyProfile;
